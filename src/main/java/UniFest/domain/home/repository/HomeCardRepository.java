package UniFest.domain.home.repository;

import UniFest.domain.home.entity.HomeCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeCardRepository extends JpaRepository<HomeCard, Long> {
    List<HomeCard> findAll();
}
