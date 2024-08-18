package UniFest.domain.waiting.repository;

import UniFest.domain.waiting.entity.ReservationStatus;
import UniFest.domain.waiting.entity.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    List<Waiting> findAllByBoothId(Long boothId);

    List<Waiting> findAllByBoothIdAndStatus(Long boothId, ReservationStatus status);

    Waiting findWaitingByDeviceIdAndId(String deviceId, Long waitingId);

    List<Waiting> findAllByDeviceIdAndStatus(String deviceId, ReservationStatus status);

    List<Waiting> findAllByBoothInAndStatus(List<Long> boothIds, ReservationStatus status);
}
