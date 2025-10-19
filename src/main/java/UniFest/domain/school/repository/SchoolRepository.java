package UniFest.domain.school.repository;

import UniFest.domain.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    List<School> findByThumbnail(String thumbnail);
    
    @Modifying
    @Query("UPDATE School s SET s.thumbnail = :newThumbnail WHERE s.thumbnail = :oldThumbnail")
    void updateThumbnail(@Param("oldThumbnail") String oldThumbnail, @Param("newThumbnail") String newThumbnail);
}
