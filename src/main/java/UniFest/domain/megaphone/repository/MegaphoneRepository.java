package UniFest.domain.megaphone.repository;

import UniFest.domain.megaphone.entity.Megaphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MegaphoneRepository extends JpaRepository<Megaphone, Long> {
}
