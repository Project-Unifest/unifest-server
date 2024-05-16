package UniFest.domain.star.repository;

import UniFest.domain.star.entity.Star;
import UniFest.dto.response.star.StarInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StarRepository extends JpaRepository<Star, Long> {

    @Query("select new UniFest.dto.response.star.StarInfo(s.id, s.name, s.img) from Star s")
    List<StarInfo> findAllStars();

}
