package UniFest.domain.waiting.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.waiting.entity.ReservationStatus;
import UniFest.domain.waiting.entity.Waiting;
import UniFest.domain.waiting.repository.WaitingRepository;
import UniFest.dto.response.waiting.WaitingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingService {
    private final WaitingRepository waitingRepository;
    private final BoothRepository boothRepository;

    public WaitingInfo addWaiting(Waiting waiting) {
        // 예약 정보를 데이터베이스에 저장
        Waiting savedWaiting = waitingRepository.save(waiting);
        return new WaitingInfo(
                savedWaiting.getBooth().getId(),
                savedWaiting.getId(),
                savedWaiting.getPartySize(),
                savedWaiting.getTel(),
                savedWaiting.getDeviceId(),
                savedWaiting.getCreatedAt(),
                savedWaiting.getUpdatedAt(),
                savedWaiting.getStatus()
        );
    }

    public WaitingInfo cancelWaiting(String deviceId, Long waitingId){
        Waiting waiting = waitingRepository.findWaitingByDeviceIdAndId(deviceId, waitingId);
        if(waiting == null) {
            return null;
        }
        waiting.setStatus(ReservationStatus.CANCELED);
        waitingRepository.save(waiting);
        return new WaitingInfo(
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

    public List<WaitingInfo> getWaitingList(Long boothId, Boolean isReserved) {
        // isReserved 가 true 이면 예약된 대기열만 조회, 아니면 전체 대기열 조회
        List<Waiting> waitingList = isReserved?
                waitingRepository.findAllByBoothIdAndStatus(boothId, ReservationStatus.RESERVED)
                : waitingRepository.findAllByBoothId(boothId);
        return waitingList.stream()
                .map(waiting -> new WaitingInfo(
                        waiting.getBooth().getId(),
                        waiting.getId(),
                        waiting.getPartySize(),
                        waiting.getTel(),
                        waiting.getDeviceId(),
                        waiting.getCreatedAt(),
                        waiting.getUpdatedAt(),
                        waiting.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public WaitingInfo callWaiting(Long id) {
        Waiting waiting = waitingRepository.findById(id).orElse(null);
        if (waiting != null) {
            if( waiting.getStatus() == ReservationStatus.COMPLETED) {
                return null;
            }
            // 사용자를 명시적으로 호출하는 명령어를 실행해야함 (일단은 미구현)
            waiting.setStatus(ReservationStatus.COMPLETED);
            waitingRepository.save(waiting);
            return new WaitingInfo(
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
        return null;
    }

    public WaitingInfo removeWaiting(Long id) {
        Waiting waiting = waitingRepository.findById(id).orElse(null);
        if (waiting == null) {
            return null;
        }
        WaitingInfo waitingInfo = new WaitingInfo(
                waiting.getBooth().getId(),
                waiting.getId(),
                waiting.getPartySize(),
                waiting.getTel(),
                waiting.getDeviceId(),
                waiting.getCreatedAt(),
                waiting.getUpdatedAt(),
                waiting.getStatus()
        );
        waitingRepository.deleteById(id); // 데이터베이스에서 삭제
        return waitingInfo;
    }
    public Long getWaitingCount(Long boothId, ReservationStatus status) {
        Booth booth = boothRepository.findByBoothId(boothId).orElse(null);
        if(booth != null){
            return Long.valueOf(waitingRepository.findAllByBoothIdAndStatus(boothId, status).size());
        }
        else {
            return null;
        }
    }
}