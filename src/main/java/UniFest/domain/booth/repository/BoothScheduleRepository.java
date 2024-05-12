package UniFest.domain.booth.repository;

import UniFest.domain.booth.entity.BoothSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BoothScheduleRepository extends JpaRepository<BoothSchedule,Long> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM BoothSchedule b where b.openDate <:today")
    void deleteBoothSchedule(LocalDate today);
}
