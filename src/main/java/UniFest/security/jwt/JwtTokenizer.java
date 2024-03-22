package UniFest.security.jwt;


import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
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

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getCategory(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public String createAccessToken(String username, String role) {

        return Jwts.builder()
                .claim("category", "access")
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMinutes))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String username, String role) {
        //TODO 리프레시에 claim 담아야하나?
        return Jwts.builder()
                .claim("category", "access")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMinutes))
                .signWith(secretKey)
                .compact();
    }

}
