package UniFest.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

@Component
@Getter
public class JwtTokenizer {

    private final SecretKey secretKey;
    private final int accessTokenExpirationMinutes;
    private final int refreshTokenExpirationMinutes;

    public JwtTokenizer(@Value("${jwt.secret}")String secret,
                        @Value("${jwt.access-token-expiration-minutes}") int accessTokenExpirationMinutes,
                        @Value("${jwt.refresh-token-expiration-minutes}") int refreshTokenExpirationMinutes

    ) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationMinutes = refreshTokenExpirationMinutes;
    }

    public Long getUserId(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("memberId", Long.class);
    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public boolean isValidDateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            Date exp = claims.getBody().getExpiration();
            //만료 날짜가 현재 시간 이후라면 true를 반환하고, 그렇지 않으면 false를 반환
            return exp.after(new Date());
        } catch (JwtException je) {
            return false;
        }

    }

    public String createAccessToken(Long memberId,String username, String role) {

        return Jwts.builder()
                .claim("category", "access")
                .claim("memberId", memberId)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(getTokenExpiration(accessTokenExpirationMinutes))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .claim("category", "refresh")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(getTokenExpiration(refreshTokenExpirationMinutes))
                .signWith(secretKey)
                .compact();
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        return calendar.getTime();
    }

}
