package UniFest.domain.home.repository;

import UniFest.domain.home.entity.HomeTip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeTipRepository extends JpaRepository<HomeTip, Long> {
    List<HomeTip> findAll();
}
