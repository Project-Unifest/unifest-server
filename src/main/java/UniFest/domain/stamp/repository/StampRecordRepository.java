package UniFest.domain.stamp.repository;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.stamp.entity.StampRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StampRecordRepository extends JpaRepository<StampRecord, Long> {
//    List<StampRecord> findByStampInfo(StampInfo stampInfo);

    List<StampRecord> findByDeviceIdAndFestival(String deviceID, Festival festival);

    Optional<StampRecord> findByDeviceIdAndBooth(String deviceId, Booth booth);
}
