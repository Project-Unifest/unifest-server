package UniFest.domain.stamp.repository;

import UniFest.domain.stamp.entity.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {

    List<Stamp> findByBoothId(Long boothId);

    List<Stamp> findByToken(String token);

    Optional<Stamp> findByTokenAndBoothId(String token, Long boothId);
}
