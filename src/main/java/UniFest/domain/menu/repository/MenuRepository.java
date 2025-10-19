package UniFest.domain.menu.repository;

import UniFest.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    
    List<Menu> findByImgUrl(String imgUrl);
    
    @Modifying
    @Query("UPDATE Menu m SET m.imgUrl = :newImgUrl WHERE m.imgUrl = :oldImgUrl")
    void updateImgUrl(@Param("oldImgUrl") String oldImgUrl, @Param("newImgUrl") String newImgUrl);
}
