package UniFest.domain.booth.repository;

import UniFest.domain.booth.entity.BoothSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoothScheduleRepository extends JpaRepository<BoothSchedule,Long> {
}
