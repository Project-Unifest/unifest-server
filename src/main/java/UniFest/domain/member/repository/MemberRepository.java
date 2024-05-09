package UniFest.domain.member.repository;

import UniFest.domain.member.entity.Member;
import UniFest.domain.member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findAllByMemberRole(MemberRole memberRole);
    boolean existsByEmail(String email);
}
