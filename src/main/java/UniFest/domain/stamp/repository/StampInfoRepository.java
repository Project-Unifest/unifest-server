package UniFest.domain.stamp.repository;

import UniFest.domain.festival.entity.Festival;
import UniFest.domain.stamp.entity.StampInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampInfoRepository extends JpaRepository<StampInfo, Long> {

//    List<StampInfo> findByBooth(Booth booth);

    StampInfo findByFestival(Festival festival);
}
