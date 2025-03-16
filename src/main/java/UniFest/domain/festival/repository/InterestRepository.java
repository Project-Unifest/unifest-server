package UniFest.domain.festival.repository;

import UniFest.domain.festival.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    boolean existsByDeviceIdAndFestivalId(String deviceId, Long festivalId);

    Optional<Interest> findByDeviceIdAndFestivalId(String deviceId, Long festivalId);

    Collection<Interest> findByDeviceId(String deviceId);
}