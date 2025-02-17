package UniFest.domain.stamp.repository;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.stamp.entity.StampInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StampInfoRepository extends JpaRepository<StampInfo, Long> {

    List<StampInfo> findByBooth(Booth booth);

    List<StampInfo> findByFestival(Festival festival);
}
