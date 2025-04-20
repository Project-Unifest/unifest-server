package UniFest.domain.waiting.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.waiting.dto.request.PostWaitingRequest;
import UniFest.domain.waiting.dto.response.WaitingInfo;
import UniFest.domain.waiting.entity.Waiting;
import UniFest.domain.waiting.repository.WaitingRepository;
import UniFest.global.infra.fcm.UserNoti;
import UniFest.global.infra.fcm.service.FcmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaitingService {
    private final WaitingRepository waitingRepository;
    private final BoothRepository boothRepository;
    private final FcmService fcmService;
    private final WaitingRedisService waitingRedisService;

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

    private List<WaitingInfo> addWaitingOrderBySingleBooth(List<Waiting> waitingList) {
        AtomicInteger order = new AtomicInteger(1);
        return waitingList.stream()
                .map(waiting -> createWaitingInfo(waiting, order.getAndIncrement()))
                .collect(Collectors.toList());
    }

    private List<WaitingInfo> addWaitingOrderByBooths(List<Waiting> waitingList) {
        return waitingList.stream()
                .collect(Collectors.groupingBy(Waiting::getBooth))
                .entrySet().stream()
                .flatMap(entry -> {
                    AtomicInteger order = new AtomicInteger(1);
                    return entry.getValue().stream()
                            .map(waiting -> {
                                Integer waitingOrder = "RESERVED".equals(waiting.getWaitingStatus())
                                        ? order.getAndIncrement() : null;
                                return createWaitingInfo(waiting, waitingOrder);
                            });
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<WaitingInfo> getMyWaitingList(String deviceId) {
        Map<Long, WaitingInfo> waitingMap = waitingRedisService.getDeviceWaitingList(deviceId);
        List<WaitingInfo> result = new ArrayList<>();
        for (Map.Entry<Long, WaitingInfo> entry : waitingMap.entrySet()) {
            Long waitingId = entry.getKey();
            WaitingInfo waitingInfo = entry.getValue();

            Long boothId = waitingInfo.getBoothId();
            Long rank = waitingRedisService.getWaitingOrderInBooth(boothId, deviceId);
            Integer waitingOrder = (rank != null) ? rank.intValue() + 1 : null;
            waitingInfo.setWaitingOrder(waitingOrder);
            result.add(waitingInfo);
        }

        return result;
    }

    @Transactional
    public WaitingInfo addWaiting(Waiting waiting) {
        Waiting savedWaiting = waitingRepository.save(waiting);
        WaitingInfo waitingInfo = createWaitingInfo(savedWaiting, 0);
        waitingRedisService.addWaitingToBooth(savedWaiting.getBooth().getId(), savedWaiting.getDeviceId());
        waitingRedisService.addWaitingToDevice(savedWaiting.getDeviceId(), savedWaiting.getId(), waitingInfo);
        Long waitingOrder = waitingRedisService.getBoothWaitingCount(savedWaiting.getBooth().getId());
        waitingInfo.setWaitingOrder(waitingOrder.intValue());
        return waitingInfo;
    }

    @Transactional
    public WaitingInfo cancelWaiting(String deviceId, Long waitingId) {
        Waiting waiting = waitingRepository.findWaitingByDeviceIdAndId(deviceId, waitingId);
        if (waiting == null) return null;

        waiting.setWaitingStatus("CANCELED");
        waitingRepository.save(waiting);

        waitingRedisService.removeWaitingFromBooth(waiting.getBooth().getId(), deviceId);
        waitingRedisService.removeWaitingFromDevice(deviceId, waitingId);

        return createWaitingInfo(waiting, null);
    }

    @Transactional
    public WaitingInfo setNoShow(Long waitingId) {
        Waiting waiting = waitingRepository.findById(waitingId).orElse(null);
        if (waiting == null) return null;
        String noShowStatus = "NOSHOW";
        waiting.setWaitingStatus(noShowStatus);
        waitingRepository.save(waiting);
        waitingRedisService.removeWaitingFromBooth(waiting.getBooth().getId(), waiting.getDeviceId());
        waitingRedisService.updateWaitingStatus(waiting.getDeviceId(), waitingId, noShowStatus);

        return createWaitingInfo(waiting, null);
    }

    @Transactional
    public WaitingInfo createWaitingIfNotExist(PostWaitingRequest req, Booth booth) {
        String deviceId = req.getDeviceId();
        Waiting exist = waitingRepository.findWaitingByDeviceIdAndBoothIdAndWaitingStatus(deviceId, booth.getId(), "RESERVED");
        if (exist == null) {
            Waiting newWaiting = new Waiting(booth, deviceId, req.getTel(), req.getPartySize(), req.getFcmToken());
            return addWaiting(newWaiting);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<WaitingInfo> getWaitingList(Long boothId, Boolean isReserved) {
        List<Waiting> waitingList = isReserved ? waitingRepository.findAllByBoothIdAndWaitingStatus(boothId, "RESERVED")
                : waitingRepository.findAllByBoothId(boothId);
        return addWaitingOrderBySingleBooth(waitingList);
    }

    @Transactional
    public WaitingInfo callWaiting(Long id) {
        WaitingInfo info = setWaitingById(id, "CALLED");

        UserNoti userNoti = UserNoti.builder()
                .title(info.getBoothName())
                .body("부스에 입장하실 차례에요!")
                .meta(Map.of("waiting_id", String.valueOf(info.getWaitingId()),
                        "booth_id", String.valueOf(info.getBoothId()),
                        "booth_name", info.getBoothName()))
                .build();
        fcmService.send(userNoti, info.getDeviceId());

        return info;
    }

    @Transactional
    public WaitingInfo completeWaiting(Long id) {
        return setWaitingById(id, "COMPLETED");
    }

    private WaitingInfo setWaitingById(Long id, String status) {
        Waiting waiting = waitingRepository.findById(id).orElse(null);
        if (waiting == null || "CANCELED".equals(waiting.getWaitingStatus())) return null;

        waiting.setWaitingStatus(status);
        waitingRepository.save(waiting);

        waitingRedisService.updateWaitingStatus(waiting.getDeviceId(), waiting.getId(), status);
        if (!"RESERVED".equals(status)) {
            waitingRedisService.removeWaitingFromBooth(waiting.getBooth().getId(), waiting.getDeviceId());
        }

        return createWaitingInfo(waiting, null);
    }

    @Transactional
    public WaitingInfo removeWaiting(Long id) {
        Waiting waiting = waitingRepository.findById(id).orElse(null);
        if (waiting == null) return null;

        WaitingInfo info = createWaitingInfo(waiting, null);
        waitingRepository.deleteById(id);

        waitingRedisService.removeWaitingFromBooth(waiting.getBooth().getId(), waiting.getDeviceId());
        waitingRedisService.removeWaitingFromDevice(waiting.getDeviceId(), waiting.getId());

        return info;
    }

    @Transactional(readOnly = true)
    public Long getWaitingCount(Long boothId, String status) {
        Booth booth = boothRepository.findByBoothId(boothId).orElse(null);
        return booth != null ? (long) waitingRepository.findAllByBoothIdAndWaitingStatus(boothId, status).size() : null;
    }

    @Transactional(readOnly = true)
    public Long getWaitingCountByBooth(Booth booth, String status) {
        return (long) waitingRepository.findAllByBoothIdAndWaitingStatus(booth.getId(), status).size();
    }
}
