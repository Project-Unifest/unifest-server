package UniFest.domain.waiting.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.waiting.entity.Waiting;
import UniFest.domain.waiting.repository.WaitingRepository;
import UniFest.dto.response.waiting.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaitingService {
    private final WaitingRepository waitingRepository;
    private final BoothRepository boothRepository;

    private WaitingInfo createWaitingInfo(Waiting waiting, Integer waitingOrder) {
        return new WaitingInfo(
                waiting.getBooth().getId(),
                waiting.getId(),
                waiting.getPartySize(),
                waiting.getTel(),
                waiting.getDeviceId(),
                waiting.getCreatedAt(),
                waiting.getUpdatedAt(),
                waiting.getWaitingStatus(),
                waitingOrder,
                waiting.getBooth().getName());
    }

    private List<WaitingInfo> addWaitingOrder(List<Waiting> waitingList) {
        AtomicInteger order = new AtomicInteger(1);
        return waitingList.stream()
                .map(waiting -> createWaitingInfo(waiting, order.getAndIncrement()))
                .collect(Collectors.toList());
    }

    private List<WaitingInfo> addWaitingOrderByBooth(List<Waiting> waitingList) {
        return waitingList.stream()
                .collect(Collectors.groupingBy(Waiting::getBooth))
                .entrySet().stream()
                .flatMap(entry -> {
                    AtomicInteger order = new AtomicInteger(1);
                    return entry.getValue().stream()
                            .map(waiting -> createWaitingInfo(waiting, order.getAndIncrement()));
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<WaitingInfo> getMyWaitingList(String deviceId) {
        List<Waiting> myWaitings = waitingRepository.findAllByDeviceIdAndWaitingStatus(deviceId, "RESERVED");
        List<Long> boothIds = myWaitings.stream()
                .map(waiting -> {
                    Booth booth = waiting.getBooth();
                    if (booth == null) {
                        throw new IllegalStateException("Booth entity is null for Waiting ID: " + waiting.getId());
                    }
                    Hibernate.initialize(booth); // 명시적으로 초기화
                    return booth.getId();
                })
                .collect(Collectors.toList());
        boothIds = boothIds.stream().distinct().collect(Collectors.toList());
        List<Waiting> allRelatedWaitings = waitingRepository.findAllByBoothIdInAndWaitingStatus(boothIds, "RESERVED");
        List<WaitingInfo> allOrderList = addWaitingOrderByBooth(allRelatedWaitings);
        return allOrderList.stream()
                .filter(waitingInfo -> waitingInfo.getDeviceId().equals(deviceId))
                .collect(Collectors.toList());
    }

    @Transactional
    public WaitingInfo addWaiting(Waiting waiting) {
        Waiting savedWaiting = waitingRepository.save(waiting);
        Long waitingOrder = getWaitingCountByBooth(waiting.getBooth(), "RESERVED");
        return createWaitingInfo(savedWaiting, Integer.valueOf(waitingOrder.intValue() + 1));
    }

    @Transactional
    public WaitingInfo cancelWaiting(String deviceId, Long waitingId) {
        Waiting waiting = waitingRepository.findWaitingByDeviceIdAndId(deviceId, waitingId);
        if (waiting == null) {
            return null;
        }
        waiting.setWaitingStatus("CANCELED");
        waitingRepository.save(waiting);
        return createWaitingInfo(waiting, null);
    }

    @Transactional
    public WaitingInfo setNoShow(Long waitingId) {
        Waiting waiting = waitingRepository.findById(waitingId).orElse(null);
        if (waiting == null) {
            return null;
        }
        waiting.setWaitingStatus("NOSHOW");
        waitingRepository.save(waiting);
        return createWaitingInfo(waiting, null);
    }

    @Transactional
    public WaitingInfo createWaitingIfNotExist(Waiting waiting) {
        Waiting existWaiting = waitingRepository.findWaitingByDeviceIdAndBoothIdAndWaitingStatus(waiting.getDeviceId(),
                waiting.getBooth().getId(), "RESERVED");
        if (existWaiting == null) {
            return addWaiting(waiting);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<WaitingInfo> getWaitingList(Long boothId, Boolean isReserved) {
        // isReserved 가 true 이면 예약된 대기열만 조회, 아니면 전체 대기열 조회
        List<Waiting> waitingList = isReserved ? waitingRepository.findAllByBoothIdAndWaitingStatus(boothId, "RESERVED")
                : waitingRepository.findAllByBoothId(boothId);

        return addWaitingOrder(waitingList);
    }

    @Transactional
    public WaitingInfo callWaiting(Long id) {
        WaitingInfo waitingInfo = setWaitingById(id, "CALLED");
        // 명시적으로 사용자를 호출한다
        return waitingInfo;
    }

    @Transactional
    public WaitingInfo completeWaiting(Long id) {
        return setWaitingById(id, "COMPLETED");
    }

    private WaitingInfo setWaitingById(Long id, String waitingStatus) {
        Waiting waiting = waitingRepository.findById(id).orElse(null);
        if (waiting != null) {
            if (waiting.getWaitingStatus().equals("CANCELED")) {
                return null;
            }
            waiting.setWaitingStatus(waitingStatus);
            waitingRepository.save(waiting);
            return createWaitingInfo(waiting, null);
        }
        return null;
    }

    @Transactional
    public WaitingInfo removeWaiting(Long id) {
        Waiting waiting = waitingRepository.findById(id).orElse(null);
        if (waiting == null) {
            return null;
        }
        WaitingInfo waitingInfo = createWaitingInfo(waiting, null);
        waitingRepository.deleteById(id); // 데이터베이스에서 삭제
        return waitingInfo;
    }

    @Transactional(readOnly = true)
    public Long getWaitingCount(Long boothId, String waitingStatus) {
        Booth booth = boothRepository.findByBoothId(boothId).orElse(null);
        if (booth != null) {
            return Long.valueOf(waitingRepository.findAllByBoothIdAndWaitingStatus(boothId, waitingStatus).size());
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Long getWaitingCountByBooth(Booth booth, String waitingStatus) {
        return Long.valueOf(waitingRepository.findAllByBoothIdAndWaitingStatus(booth.getId(), waitingStatus).size());
    }
}