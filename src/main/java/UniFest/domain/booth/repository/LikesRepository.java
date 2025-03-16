package UniFest.domain.booth.repository;

import UniFest.domain.booth.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByBoothIdAndToken(Long boothId, String token);

    List<Likes> findAllLikesByToken(String token);
}
