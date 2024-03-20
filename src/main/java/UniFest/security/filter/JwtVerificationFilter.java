package UniFest.security.filter;

import UniFest.exception.jwt.TokenNotValidateException;
import UniFest.security.jwt.JwtTokenizer;
import UniFest.security.userdetails.MemberDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    //jwt를 검증하는 필터

    private final JwtTokenizer jwtTokenizer;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            //Bearer부분 제거 후 순수토큰 값
            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
            setAuthToContextHolder(accessToken);
            filterChain.doFilter(request,response);
        } catch (SignatureException e) {
            throw new TokenNotValidateException("JWT 시그니처 정보가 잘못되었습니다.");
        } catch (ExpiredJwtException e) {
            throw new TokenNotValidateException("JWT 유효기한이 만료되었습니다.");
        } catch (Exception e) {
            throw new TokenNotValidateException("JWT 토큰을 검증하는 데 실패하였습니다.");
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return headerNotValidate(request) || isLoginLogoutRequest(request) ||
                isSignUpRequest(request);
    }

    private void setAuthToContextHolder(String accessToken) {
        String username = jwtTokenizer.getUsername(accessToken);
        String role = jwtTokenizer.getRole(accessToken);
        //현재 role 은 ROLE_XXXX 형태
        //포맷 바꿔서 저장
        String roleValue = role.replace("ROLE_", "");
        MemberDetails memberDetails = new MemberDetails(username,"temppassword",roleValue);
        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private boolean headerNotValidate(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header == null || !header.startsWith("Bearer");
    }

    private boolean isLoginLogoutRequest(HttpServletRequest request) {
        boolean isLoginRequest = request.getRequestURI().contains("/login");
        boolean isLogoutRequest = request.getRequestURI().contains("/logout");
        return isLoginRequest || isLogoutRequest;
    }

    private boolean isSignUpRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("/v1/members") &&
                request.getMethod().equalsIgnoreCase("POST");
    }

//    private boolean isRefreshRequest(HttpServletRequest request) {
//        return request.getRequestURI().contains("/auth/reissue");
//    }

}
