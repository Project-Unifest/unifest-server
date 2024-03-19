package UniFest.domain.member.service;

import UniFest.domain.member.entity.Member;
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

        Member member = Member.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .assign(request.getAssign())
                .phoneNum(request.getPhoneNum())
                .build();

        return memberRepository.save(member).getId();
    }

    private void verifyExistsEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberEmailExistException();
        }
    }
}
