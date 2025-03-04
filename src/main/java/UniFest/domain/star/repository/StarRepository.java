package UniFest.domain.star.repository;

import UniFest.domain.star.entity.Star;
import UniFest.domain.star.dto.response.StarInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StarRepository extends JpaRepository<Star, Long> {

    @Query("select new UniFest.dto.response.star.StarInfo(s.id, s.name, s.img) from Star s")
    List<StarInfo> findAllStars();

    @Query("SELECT new UniFest.dto.response.star.StarInfo(s.id, s.name, s.img) " +
            "FROM Star s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<StarInfo> findByNameContainingIgnoreCase(@Param("name") String name);
}
