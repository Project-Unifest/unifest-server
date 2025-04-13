package UniFest.domain.waiting.service;

import UniFest.domain.waiting.dto.response.WaitingInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WaitingRedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String WAITING_ZSET_KEY = "waiting:booth:%d";
    private static final String WAITING_HASH_KEY = "waiting:device:%s";

    private String generateWaitingZsetKey(Long boothId) {
        return String.format(WAITING_ZSET_KEY, boothId);
    }

    private String generateWaitingHashKey(String deviceId) {
        return String.format(WAITING_HASH_KEY, deviceId);
    }

    public void addWaitingToBooth(Long boothId, String deviceId) {
        String zsetKey = generateWaitingZsetKey(boothId);
        redisTemplate.opsForZSet().add(zsetKey, deviceId, Instant.now().toEpochMilli());
    }

    public void addWaitingToDevice(String deviceId, Long waitingId, WaitingInfo info) {
        String hashKey = generateWaitingHashKey(deviceId);
        try {
            String value = objectMapper.writeValueAsString(info);
            redisTemplate.opsForHash().put(hashKey, String.valueOf(waitingId), value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize WaitingInfo", e);
        }
    }

    public void removeWaitingFromBooth(Long boothId, String deviceId) {
        String zsetKey = generateWaitingZsetKey(boothId);
        redisTemplate.opsForZSet().remove(zsetKey, deviceId);
    }

    public void removeWaitingFromDevice(String deviceId, Long waitingId) {
        String hashKey = generateWaitingHashKey(deviceId);
        redisTemplate.opsForHash().delete(hashKey, String.valueOf(waitingId));
    }

    public void updateWaitingInfo(String deviceId, Long waitingId, WaitingInfo info) {
        String hashKey = generateWaitingHashKey(deviceId);
        try {
            String value = objectMapper.writeValueAsString(info);
            redisTemplate.opsForHash().put(hashKey, String.valueOf(waitingId), value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to update WaitingInfo", e);
        }
    }

    public void updateWaitingStatus(String deviceId, Long waitingId, String newStatus) {
        String hashKey = generateWaitingHashKey(deviceId);
        Object raw = redisTemplate.opsForHash().get(hashKey, String.valueOf(waitingId));
        if (raw == null) return;
        try {
            WaitingInfo info = objectMapper.readValue(raw.toString(), WaitingInfo.class);
            info.setStatus(newStatus);
            updateWaitingInfo(deviceId, waitingId, info);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update status in WaitingInfo", e);
        }
    }

    public Map<Long, WaitingInfo> getDeviceWaitingList(String deviceId) {
        String hashKey = generateWaitingHashKey(deviceId);
        Map<Object, Object> rawData = redisTemplate.opsForHash().entries(hashKey);

        Map<Long, WaitingInfo> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : rawData.entrySet()) {
            try {
                Long key = Long.valueOf(entry.getKey().toString());
                WaitingInfo value = objectMapper.readValue(entry.getValue().toString(), WaitingInfo.class);
                result.put(key, value);
            } catch (Exception e) {
                throw new RuntimeException("Failed to deserialize WaitingInfo", e);
            }
        }
        return result;
    }

    public Long getBoothWaitingCount(Long boothId) {
        String zsetKey = generateWaitingZsetKey(boothId);
        return redisTemplate.opsForZSet().zCard(zsetKey);
    }

    public Long getWaitingOrderInBooth(Long boothId, String deviceId) {
        String zsetKey = generateWaitingZsetKey(boothId);
        return  redisTemplate.opsForZSet().rank(zsetKey, deviceId);
    }
}
