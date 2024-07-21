package UniFest.domain.waiting.service;

import UniFest.domain.waiting.entity.ReservationStatus;
import UniFest.domain.waiting.entity.Waiting;
import UniFest.domain.waiting.repository.WaitingRepository;
import UniFest.dto.response.waiting.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingService {
    private final RedisTemplate<String, String> redisTemplate;
    private final WaitingRepository waitingRepository;
    private final AtomicLong counter = new AtomicLong();

    private String getZSetKey(Long boothId) {
        return "waitingList:" + boothId;
    }

    public Waiting addWaiting(Waiting waiting) {
        // 예약 정보를 데이터베이스에 저장
        Waiting savedWaiting = waitingRepository.save(waiting);

        // Redis에 우선순위 큐로 저장
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String zsetKey = getZSetKey(waiting.getBooth().getId());
        double score = System.currentTimeMillis(); // 현재 시간 사용
        String value = savedWaiting.getId().toString() + ":" + counter.incrementAndGet(); // 고유 인덱스 추가
        zSetOps.add(zsetKey, value, score);

        return savedWaiting;
    }

    public List<WaitingInfo> getWaitingList(Long boothId) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String zsetKey = getZSetKey(boothId);
        Set<String> waitingSet = zSetOps.range(zsetKey, 0, -1);
        List<Long> waitingIds = waitingSet.stream()
                .map(waitingId -> Long.parseLong(waitingId.split(":")[0]))
                .collect(Collectors.toList());


        List<Waiting> waitingList = waitingRepository.findAllByBoothIdAndStatus(boothId, ReservationStatus.RESERVED);
        List<WaitingInfo> retList = new ArrayList<>();
        for (Waiting waiting : waitingList) {
            if(waitingIds.contains(waiting.getId())) {
                retList.add(new WaitingInfo(
                        waiting.getBooth().getId(),
                        waiting.getId(),
                        waiting.getPartySize(),
                        waiting.getTel(),
                        waiting.getDeviceId(),
                        waiting.getCreatedAt(),
                        waiting.getUpdatedAt(),
                        waiting.getStatus()
                ));
            }
        }
        return retList;
    }

    public List<WaitingInfo> getAllWaitingList(Long boothId) {
        List<Waiting> waitingList = waitingRepository.findAllByBoothId(boothId);
        List<WaitingInfo> retList = new ArrayList<>();
        for (Waiting waiting : waitingList) {
            retList.add(new WaitingInfo(
                    waiting.getBooth().getId(),
                    waiting.getId(),
                    waiting.getPartySize(),
                    waiting.getTel(),
                    waiting.getDeviceId(),
                    waiting.getCreatedAt(),
                    waiting.getUpdatedAt(),
                    waiting.getStatus()
            ));
        }
        return retList;
    }

    public void removeWaiting(Long boothId, Long id) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String zsetKey = getZSetKey(boothId);
        Set<String> values = zSetOps.rangeByLex(zsetKey, RedisZSetCommands.Range.range().gte(id.toString()).lte(id.toString()));
        for (String value : values) {
            // REDIS 에서 제거
            zSetOps.remove(zsetKey, value);
        }
        waitingRepository.deleteById(id); // 데이터베이스에서 삭제
    }

    public WaitingInfo popHighestPriorityWaiting(Long boothId, int partySize) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String zsetKey = getZSetKey(boothId);
        Set<String> highestPrioritySet = zSetOps.range(zsetKey, 0, -1); // 모든 항목 조회


        WaitingInfo ret = null;
        if (highestPrioritySet != null && !highestPrioritySet.isEmpty()) {
            for (String highestPriorityId : highestPrioritySet) {
                Long waitingId = Long.parseLong(highestPriorityId.split(":")[0]);
                Waiting waiting = waitingRepository.findById(waitingId).orElse(null);
                if (waiting != null && waiting.getPartySize() <= partySize) {
                    // REDIS 에서 제거
                    zSetOps.remove(zsetKey, highestPriorityId);

                    // 데이터베이스에서 상태를 변경한다

                    waiting.setStatus(ReservationStatus.COMPLETED);
                    waitingRepository.save(waiting);
                    ret = new WaitingInfo(
                            waiting.getBooth().getId(),
                            waiting.getId(),
                            waiting.getPartySize(),
                            waiting.getTel(),
                            waiting.getDeviceId(),
                            waiting.getCreatedAt(),
                            waiting.getUpdatedAt(),
                            waiting.getStatus()
                    );
                }
            }
        }
        return ret;
    }

    public Long getWaitingCount(Long boothId) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String zsetKey = getZSetKey(boothId);
        return zSetOps.size(zsetKey);
    }
}