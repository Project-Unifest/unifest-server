package UniFest.domain.fcm.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;


@Repository
public class FcmRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public FcmRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSyncToken(String deviceId, String fcmToken) {
        String key = "sync:" + deviceId;
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, fcmToken, 14, TimeUnit.DAYS); // TTL to 14 days
    }

    public String getSyncToken(String deviceId) {
        String key = "sync:" + deviceId;
        return redisTemplate.opsForValue().get(key);
    }
}
