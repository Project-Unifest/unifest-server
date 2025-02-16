package UniFest.domain.waiting.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.fcm.service.FcmService;
import UniFest.domain.waiting.entity.Waiting;
import UniFest.domain.waiting.repository.WaitingRepository;
import UniFest.dto.request.waiting.PostWaitingRequest;
import UniFest.dto.response.waiting.WaitingInfo;
import UniFest.exception.announcement.FcmFailException;
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
        Map<Long, List<Waiting>> groupedByBooth = new LinkedHashMap<>(); // {boothId: List<Waiting>}
        List<WaitingInfo> result = new ArrayList<>();

        // booth id 기준 그룹화 (이미 정렬됨)
        for (Waiting waiting : waitingList) {
            groupedByBooth.computeIfAbsent(waiting.getBooth().getId(), k -> new ArrayList<>()).add(waiting);
        }

        for (Map.Entry<Long, List<Waiting>> entry : groupedByBooth.entrySet()) {
            List<Waiting> boothWaitings = entry.getValue();

            // ✅ 기존 RESERVED 웨이팅 개수 확인하여 order 초기값 설정
            int order = (int) boothWaitings.stream()
                    .filter(w -> "RESERVED".equals(w.getWaitingStatus()))
                    .count() - boothWaitings.size() + 1;

            for (Waiting waiting : boothWaitings) {
                Integer waitingOrder = "RESERVED".equals(waiting.getWaitingStatus()) ? order++ : null;
                result.add(createWaitingInfo(waiting, waitingOrder));
            }
        }

        return result;
    }
    @Transactional
    public List<WaitingInfo> getMyWaitingList(String deviceId) {
        List<String> statuses = Arrays.asList("RESERVED", "CALLED", "NOSHOW");
        List<Waiting> myWaitings = waitingRepository.findAllWithBoothByDeviceIdAndWaitingStatusIn(deviceId, statuses);

        Set<Long> boothIds = myWaitings.stream()
                .map(waiting->waiting.getBooth().getId())
                .collect(Collectors.toSet());


        List<Waiting> allRelatedWaitings = waitingRepository.findAllByBoothIdInAndWaitingStatusInOrderByBoothIdAscCreatedAtAsc(new ArrayList<>(boothIds), statuses);
        return addWaitingOrderByBooth(allRelatedWaitings);
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
    public WaitingInfo createWaitingIfNotExist(PostWaitingRequest waitingRequest, Booth existBooth) {

        String deviceId = waitingRequest.getDeviceId();
        String fcmToken = waitingRequest.getFcmToken();
        Long boothId = waitingRequest.getBoothId();
        Waiting existWaiting = waitingRepository.findWaitingByDeviceIdAndBoothIdAndWaitingStatus(deviceId,
                boothId, "RESERVED");
        if (existWaiting == null) {
            Waiting newWaiting = new Waiting(
                    existBooth,
                    deviceId,
                    waitingRequest.getTel(),
                    waitingRequest.getPartySize(),
                    fcmToken
            );
            return addWaiting(newWaiting);
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
        String fcmToken = fcmService.getFcmToken(waitingInfo.getDeviceId());
        String waitingTitle = waitingInfo.getBoothName();
        String waitingBody = "부스에 입장하실 차례에요!";
        if(fcmToken!=null){
            Notification notification = Notification.builder()
                    .setTitle(waitingTitle)
                    .setBody(waitingBody)
                    .build();
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .putData("waiting_id", String.valueOf(waitingInfo.getWaitingId()))
                    .putData("booth_id", String.valueOf(waitingInfo.getBoothId()))
                    .putData("booth_name", waitingInfo.getBoothName())
                    .build();
            try{
                fcmService.send(message);
            } catch (FirebaseMessagingException e) {
                throw new FcmFailException(e.getMessage());
            }
            return waitingInfo;
        }
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