package UniFest.domain.booth.repository;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoothRepository extends JpaRepository<Booth,Long> {
    @Query("SELECT b FROM Booth b WHERE b.festival = ?1 ORDER BY SIZE(b.likesList) DESC limit 5")
    List<Booth> findTop5ByFestivalOrderByLikesListSizeDesc(Festival festival);
}
