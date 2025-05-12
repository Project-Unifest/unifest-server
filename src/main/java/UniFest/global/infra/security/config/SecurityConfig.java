package UniFest.global.infra.security.config;

import UniFest.global.infra.security.filter.LoginFilter;
import UniFest.global.infra.security.filter.JwtExceptionFilter;
import UniFest.global.infra.security.filter.JwtVerificationFilter;
import UniFest.global.infra.security.jwt.JwtTokenizer;
import UniFest.global.infra.redis.RedisRepository;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//springsecurity 최신버전 (6.x)이상부터는 메서드체이닝을 지양하고 람다식을 통해 함수형으로 설계
public class SecurityConfig{
    private static final String USER_WEB_DEPRECATED = "https://unifest.netlify.app";
    private static final String USER_APP = "https://www.unifest.app";
    private static final String ADMIN_WEB = "https://project-unifest.github.io";
    private static final String LOCALHOST = "http://localhost:3000";
    private static final String USER_WEB = "https://unifest-web-245.pages.dev/";
    private static final String WEB_PREVIEW = "*.unifest-web-245.pages.dev";

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
                        configuration.setAllowedOrigins(List.of(LOCALHOST,USER_WEB,USER_APP,ADMIN_WEB, USER_WEB_DEPRECATED, WEB_PREVIEW));
                        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(List.of("*"));
                        configuration.setMaxAge(10000L);
                        configuration.setExposedHeaders(List.of("RefreshToken", HttpHeaders.AUTHORIZATION));
                        configuration.setAllowCredentials(true);

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
                .csrf(AbstractHttpConfigurer::disable);
        //폼로그인 비활성화
        http
                .formLogin(AbstractHttpConfigurer::disable);
        //http basic 비활성화
        http
                .httpBasic((AbstractHttpConfigurer::disable));
        //경로별 인가작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.POST, "/festival/*/interest").permitAll() // 관심 축제 등록 허용
                        .requestMatchers(HttpMethod.DELETE, "/festival/*/interest").permitAll() // 관심 축제 해제 허용
                        .requestMatchers("/admin/").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/festival/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/festival").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/school").hasRole("ADMIN")
                        .requestMatchers("/megaphone/subscribe").permitAll()
                        .requestMatchers(HttpMethod.POST, "/megaphone").hasRole("ADMIN") // 확성기 메세지 등록은 ADMIN 만
                        .requestMatchers(HttpMethod.PATCH,"/members/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/members").hasRole("ADMIN") // 전체 멤버 조회는 ADMIN만
                        .requestMatchers(HttpMethod.GET, "/api/booths").permitAll()
                        .requestMatchers("/api/booths").hasAnyRole("ADMIN","VERIFIED")
                        .requestMatchers(HttpMethod.POST, "/members").permitAll() // 회원가입은 아무나 가능
                        .requestMatchers(HttpMethod.PATCH, "/members/my").authenticated() // 본인 정보 조회는 인증된 사용자만
                        .requestMatchers(HttpMethod.GET, "/members/my").authenticated() // 본인 정보 조회는 인증된 사용자만
                        //.requestMatchers(HttpMethod.DELETE, "/members/my").authenticated() // 본인 탈퇴는 인증된 사용자만
                        .requestMatchers(HttpMethod.GET, "/members/**").hasRole("ADMIN") // 회원 정보 조회는 ADMIN만
                        .requestMatchers(HttpMethod.DELETE, "/members/**").hasRole("ADMIN") // 회원 정보 조회는 ADMIN만
                        .requestMatchers(HttpMethod.PATCH, "/stamps/*/stampEnabled").hasRole("ADMIN") //부스 스탬프 설정 변경은 ADMIN만
                        .requestMatchers(HttpMethod.POST, "/waiting/pin/check").permitAll()
                        .requestMatchers(HttpMethod.POST, "/waiting/pin/**").hasAnyRole("ADMIN", "VERIFIED")
                        .requestMatchers(HttpMethod.GET, "/waiting/pin/**").hasAnyRole("ADMIN", "VERIFIED")
                        //h2 접속 설정
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
