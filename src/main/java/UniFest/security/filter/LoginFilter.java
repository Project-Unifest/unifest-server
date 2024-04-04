package UniFest.security.filter;

import UniFest.dto.request.auth.LoginRequest;
import UniFest.security.jwt.JwtTokenizer;
import UniFest.security.redis.RedisRepository;
import UniFest.security.userdetails.MemberDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    // 로그인 요청받는다
    // 폼로그인 비활성화 -> UsernamePasswordAuthenticationFilter 상속받은 필터 직접 작성

    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final RedisRepository redisRepository;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //DB에서 로그인정보 검증
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

        //요청에서 username, password 추출
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        //UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, password, null);

        //authenticationManager에게 토큰 전달
        return authenticationManager.authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //로그인 성공시 실행되는 함수 -> jwt 발급
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        //email 획득
        String username = memberDetails.getUsername();
        //id 획득
        Long memberId = memberDetails.getMemberId();
        //권한 획득
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        //여기서 role은 ROLE_XXXX 형태

        String access = jwtTokenizer.createAccessToken(memberId,username,role);
        String refresh = jwtTokenizer.createRefreshToken();

        //redis에 refresh : email 형태로 저장
        redisRepository.saveRefreshToken(refresh, username);

        //RFC 7235 방식
        response.setHeader("Authorization", "Bearer " + access);
        response.setHeader("RefreshToken", refresh);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //로그인 실패시 실행되는 함수
        response.setStatus(401);
    }


}
