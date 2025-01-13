package UniFest.domain.festival.repository;

import UniFest.domain.festival.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
