package UniFest.domain.home.repository;

import UniFest.domain.home.entity.HomeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HomeCardRepository extends JpaRepository<HomeCard, Long> {
    List<HomeCard> findAll();
    
    List<HomeCard> findByThumbnailImgUrl(String thumbnailImgUrl);
    List<HomeCard> findByDetailImgUrl(String detailImgUrl);
    
    @Modifying
    @Query("UPDATE HomeCard h SET h.thumbnailImgUrl = :newThumbnailImgUrl WHERE h.thumbnailImgUrl = :oldThumbnailImgUrl")
    void updateThumbnailImgUrl(@Param("oldThumbnailImgUrl") String oldThumbnailImgUrl, @Param("newThumbnailImgUrl") String newThumbnailImgUrl);
    
    @Modifying
    @Query("UPDATE HomeCard h SET h.detailImgUrl = :newDetailImgUrl WHERE h.detailImgUrl = :oldDetailImgUrl")
    void updateDetailImgUrl(@Param("oldDetailImgUrl") String oldDetailImgUrl, @Param("newDetailImgUrl") String newDetailImgUrl);
}
