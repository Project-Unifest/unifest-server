package UniFest.domain.waiting.repository;

import UniFest.domain.waiting.entity.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    List<Waiting> findAllByBoothId(Long boothId);

    Waiting findWaitingByDeviceIdAndId(String deviceId, Long waitingId);

    Waiting findWaitingByDeviceIdAndBoothIdAndWaitingStatus(String deviceId, Long boothId, String waitingStatus);
    // prune all the ENUM Based stuffs
    List<Waiting> findAllByBoothIdAndWaitingStatus(Long boothId, String waitingStatus);

    Waiting findWaitingByDeviceIdAndId(String deviceId, Long waitingId);

    List<Waiting> findAllByDeviceIdAndStatus(String deviceId, ReservationStatus status);

    List<Waiting> findAllByBoothIdInAndStatus(List<Long> boothIds, ReservationStatus status);
}
