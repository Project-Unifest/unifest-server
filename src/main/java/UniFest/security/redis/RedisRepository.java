package UniFest.security.redis;


import UniFest.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenizer jwtTokenizer;

    public void saveRefreshToken(String refreshToken, String email) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken, email, jwtTokenizer.getRefreshTokenExpirationMinutes(), TimeUnit.MINUTES);
    }

    public void expireRefreshToken(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

    public boolean findRefreshToken(String refreshToken) {
        return redisTemplate.hasKey(refreshToken);
    }

    public String getEmail(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(refreshToken);
    }
}
