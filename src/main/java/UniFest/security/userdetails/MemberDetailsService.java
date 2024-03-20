package UniFest.security.userdetails;

import UniFest.domain.member.entity.Member;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberNotFoundException());
        //TODO 추후 최근접속일, 멤버 탈퇴 등 로직 고려해서 추가
        return new MemberDetails(findMember.getEmail(), findMember.getPassword(),findMember.getMemberRole().getValue());
    }
}
