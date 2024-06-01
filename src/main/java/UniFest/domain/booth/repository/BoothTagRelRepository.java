package UniFest.domain.booth.repository;

import UniFest.domain.booth.entity.BoothTagRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoothTagRelRepository extends JpaRepository<BoothTagRel,Long> {

}
