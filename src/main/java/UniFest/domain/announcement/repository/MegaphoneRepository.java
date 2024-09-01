package UniFest.domain.announcement.repository;

import UniFest.domain.announcement.entity.Megaphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MegaphoneRepository extends JpaRepository<Megaphone, Long> {
}