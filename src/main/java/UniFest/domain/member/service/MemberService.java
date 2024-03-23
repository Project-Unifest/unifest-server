package UniFest.domain.member.service;

import UniFest.domain.member.entity.Member;
import UniFest.domain.member.entity.MemberRole;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.dto.request.member.MemberSignUpRequest;
import UniFest.exception.member.MemberEmailExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long createMember(MemberSignUpRequest request) {
        verifyExistsEmail(request.getEmail());
        String encryptedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        //if(request.getEmail.equals("총학아이디"){
        // TODO 권한 ADIMN 부여
        // }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .club(request.getClub())
                .phoneNum(request.getPhoneNum())
                .memberRole(MemberRole.NORMAL)
                .build();

        return memberRepository.save(member).getId();
    }

    private void verifyExistsEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberEmailExistException();
        }
    }
}
