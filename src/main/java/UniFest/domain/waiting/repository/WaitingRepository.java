package UniFest.domain.waiting.repository;

import UniFest.domain.waiting.entity.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    List<Waiting> findAllByBoothId(Long boothId);

    Waiting findWaitingByDeviceIdAndId(String deviceId, Long waitingId);

    Waiting findWaitingByDeviceIdAndBoothIdAndWaitingStatus(String deviceId, Long boothId, String waitingStatus);

    // prune all the ENUM Based stuffs
    List<Waiting> findAllByBoothIdAndWaitingStatus(Long boothId, String waitingStatus);

    List<Waiting> findAllByDeviceIdAndWaitingStatusIn(String deviceId, List<String> statuses);
    List<Waiting> findAllByBoothIdInAndWaitingStatusIn(List<Long> boothIds, List<String> statuses);


    // New for waiting opt
    @Query("SELECT w FROM Waiting w JOIN FETCH w.booth WHERE w.deviceId = :deviceId AND w.waitingStatus IN :statuses ORDER BY w.booth.id, w.createdAt ASC")
    List<Waiting> findAllWithBoothByDeviceIdAndWaitingStatusIn(@Param("deviceId") String deviceId, @Param("statuses") List<String> statuses);

    List<Waiting> findAllByBoothIdInAndWaitingStatusInOrderByBoothIdAscCreatedAtAsc(List<Long> boothIds, List<String> statuses);
}
