package UniFest.domain.star.repository;

import UniFest.domain.star.entity.Enroll;
import UniFest.dto.response.star.EnrollInfo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    @Query("select new UniFest.dto.response.star.EnrollInfo(e.festival.id, s.id, s.name, s.img) from Enroll e"
            + " join Star s on e.star.id=s.id"
            + " where e.visitDate=:date")
    List<EnrollInfo> findByDate(@Param("date") LocalDate date);


    @Query("SELECT e FROM Enroll e WHERE e.festival.id = :festivalId")
    List<Enroll> findByFestivalId(@Param("festivalId") Long festivalId);
}
