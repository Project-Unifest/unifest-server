package UniFest.config;

import UniFest.security.filter.LoginFilter;
import UniFest.security.filter.JwtExceptionFilter;
import UniFest.security.filter.JwtVerificationFilter;
import UniFest.security.jwt.JwtTokenizer;
import UniFest.security.redis.RedisRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//springsecurity 최신버전 (6.x)이상부터는 메서드체이닝을 지양하고 람다식을 통해 함수형으로 설계
public class SecurityConfig{
    private static final String APP_ADMIN = "https://www.unifest.app/";
    private static final String LOCALHOST = "http://localhost:3000";

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtTokenizer jwtTokenizer;

    private final RedisRepository redisRepository;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //cors 설정
        http
                .cors((cors) -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(List.of(LOCALHOST,APP_ADMIN));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(10000L);
                        configuration.setExposedHeaders(List.of("RefreshToken", HttpHeaders.AUTHORIZATION));

                        return configuration;
                    }
                }));
        // frameOption 비활성화 -> h2접속 설정
        http
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                );
        //csrf 비활성화
        http
                .csrf((auth) -> auth.disable());
        //폼로그인 비활성화
        http
                .formLogin((auth) -> auth.disable());
        //http basic 비활성화
        http
                .httpBasic((auth -> auth.disable()));
        //경로별 인가작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.PATCH,"/members/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/booths").permitAll()
                        .requestMatchers("/api/booths").hasAnyRole("ADMIN","VERIFIED")
                        //TODO 상위 5개 부스 조회 시 VERIFIED여도 막힘
                        //h2접속 설정
                        .requestMatchers("/h2-console/**", "/favicon.ico").permitAll()
                        .anyRequest().permitAll());

        //jwt에서 세션 stateless
        http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //필터 설정
        http.addFilterBefore(new JwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtVerificationFilter(jwtTokenizer), LoginFilter.class);
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtTokenizer, redisRepository)
                , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
