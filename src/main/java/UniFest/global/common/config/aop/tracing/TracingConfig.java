package UniFest.global.common.config.aop.tracing;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Tracer.SpanInScope;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.pattern.PathPattern;

@Configuration
public class TracingConfig {
    // 1) HTTP Body 캐싱 필터 등록
    @Component
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class RequestBodyCachingFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            // GET 등 바디 없는 요청은 그냥 넘기고
            if (!(request instanceof ContentCachingRequestWrapper)) {
                request = new ContentCachingRequestWrapper(request);
            }
            filterChain.doFilter(request, response);
        }
    }

    // 2) AOP Aspect
    @Aspect
    @Component
    public static class TracingAspect {

        private static final Logger log = LoggerFactory.getLogger(TracingAspect.class);
        private final Tracer tracer;
        private static final Set<String> ALLOWED_PATH_PARAMS = Set.of("booth-id", "menu-id", "boothId","waitingId","festival-id");

        public TracingAspect(Tracer tracer) {
            this.tracer = tracer;
            System.out.println("TracingAspect loaded!");
        }

        // Controller
        @Around("@within(org.springframework.web.bind.annotation.RestController)")
        public Object traceController(ProceedingJoinPoint pjp) throws Throwable {
            // Span 생성
            Span span = tracer.nextSpan().name(pjp.getSignature().getName()).start();

            long startMs = System.currentTimeMillis();

            // HTTP 요청 정보
            ContentCachingRequestWrapper req = getRequestWrapper();
            String method = req.getMethod();

            Object patternAttr = req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            String urlPath = req.getRequestURI();
            String route;
            if (patternAttr instanceof PathPattern pp) {
                route = pp.getPatternString();
            } else if (patternAttr != null) {
                route = patternAttr.toString();
            } else {
                route = urlPath;
            }
            String query = Optional.ofNullable(req.getQueryString()).orElse("");
            String fullUrl = query.isEmpty() ? urlPath : urlPath + "?" + query;

            @SuppressWarnings("unchecked")
            Map<String, String> pathVars = (Map<String, String>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

            // Request Body
            String body = "";
            byte[] buf = req.getContentAsByteArray();
            if (buf.length > 0) {
                body = new String(buf, StandardCharsets.UTF_8);
            }

            try (SpanInScope ws = tracer.withSpan(span)) {
                // MDC 설정
                MDC.put("traceId", span.context().traceId());
                MDC.put("spanId",  span.context().spanId());

                // Log
                log.info("[ENTRY] {} {}", method, fullUrl);
                if (!body.isEmpty()) {
                    log.debug("[ENTRY-BODY] {}", body);
                }

                // Tag: HTTP Method, URI
                span.tag("http.method", method);
                span.tag("http.route", route);
                if (!body.isEmpty()) {
                    span.tag("http.request_body",
                            body.length() < 512 ? body : body.substring(0, 512) + "...");
                }

                // 실제 호출
                return pjp.proceed();

            } catch (Throwable t) {
                span.error(t);
                span.tag("error.message", t.getMessage());
                span.tag("url.path", urlPath);
                if (!query.isEmpty()) span.tag("url.query", truncate(query));

                if (pathVars != null && !pathVars.isEmpty()) {
                    pathVars.forEach((k, v) -> {
                        if (ALLOWED_PATH_PARAMS.isEmpty() || ALLOWED_PATH_PARAMS.contains(k)) {
                            span.tag("path.param." + k, truncate(v));
                        }
                    });
                }
                log.error("[ERROR] {} : {}", pjp.getSignature().toShortString(), t.toString());
                throw t;
            } finally {
                long took = System.currentTimeMillis() - startMs;
                log.info("[EXIT ] {} {} ({} ms)", method, fullUrl, took);

                // Tag: duration
                span.tag("duration.ms", String.valueOf(took));

                span.end();
                MDC.clear();
            }
        }

        // Service/Repository
        @Around("@within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository)")
        public Object traceServiceRepo(ProceedingJoinPoint pjp) throws Throwable {
            Span span = tracer.nextSpan().name(pjp.getSignature().getName()).start();
            try (SpanInScope ws = tracer.withSpan(span)) {
                long startMs = System.currentTimeMillis();
                log.info("[CALL ] {}", pjp.getSignature().toShortString());

                Object result = pjp.proceed();

                long took = System.currentTimeMillis() - startMs;
                log.info("[DONE ] {} ({} ms)", pjp.getSignature().toShortString(), took);
                span.tag("duration.ms", String.valueOf(took));
                return result;

            } catch (Throwable t) {
                span.error(t);
                span.tag("error.message", t.getMessage());
                log.error("[ERROR] {} : {}", pjp.getSignature().toShortString(), t.toString());
                throw t;
            } finally {
                span.end();
            }
        }

        private ContentCachingRequestWrapper getRequestWrapper() {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (!(requestAttributes instanceof ServletRequestAttributes attrs)) {
                throw new IllegalStateException("No servlet request bound to current thread");
            }
            HttpServletRequest request = attrs.getRequest();
            if (request instanceof ContentCachingRequestWrapper wrapper) {
                return wrapper;
            }
            return new ContentCachingRequestWrapper(request);
        }

        private HttpServletResponse getResponse() {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if(requestAttributes instanceof ServletRequestAttributes attrs) {
                return attrs.getResponse();
            }
            return null;
        }

        private String truncate(String s) {
            if (s == null) return "";
            return s.length() <= 256 ? s : s.substring(0, 256) + "...";
        }
    }
}
