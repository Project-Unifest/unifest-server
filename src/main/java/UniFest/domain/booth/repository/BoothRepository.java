package UniFest.domain.booth.repository;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BoothRepository extends JpaRepository<Booth,Long> {
    @Query("SELECT b FROM Booth b WHERE b.festival = ?1 AND b.enabled = true ORDER BY SIZE(b.likesList) DESC limit 5")
    List<Booth> findTop5ByFestivalOrderByLikesListSizeDesc(Festival festival);



    @Query("select distinct b from Booth b "+
            "left join fetch b.menuList "+
            "where b.id = :boothId")
    Optional<Booth> findByBoothId(@Param("boothId") Long boothId);
    List<Booth> findAllByFestivalAndEnabled(Festival festival, boolean enabled);

}
