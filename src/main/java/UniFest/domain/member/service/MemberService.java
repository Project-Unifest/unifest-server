package UniFest.domain.member.service;

import UniFest.domain.member.entity.Member;
import UniFest.domain.member.entity.MemberRole;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.domain.school.entity.School;
import UniFest.domain.school.repository.SchoolRepository;
import UniFest.dto.request.member.MemberSignUpRequest;
import UniFest.dto.response.member.MemberDetailResponse;
import UniFest.exception.SchoolNotFoundException;
import UniFest.exception.member.MemberEmailExistException;
import UniFest.exception.member.MemberNotFoundException;
import UniFest.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final SchoolRepository schoolRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long createMember(MemberSignUpRequest request) {
        verifyExistsEmail(request.getEmail());
        String encryptedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        //if(request.getEmail.equals("총학아이디"){
        // TODO 권한 ADIMN 부여
        // }

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(SchoolNotFoundException::new);

        Member member = Member.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .school(school)
                .phoneNum(request.getPhoneNum())
                .memberRole(MemberRole.VERIFIED)
                .build();

        return memberRepository.save(member).getId();
    }

    public MemberDetailResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        MemberDetailResponse response = new MemberDetailResponse(member);
        return response;
    }

    private void verifyExistsEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberEmailExistException();
        }
    }
    @Transactional
    public Long updateMemberRole(Long memberId, MemberRole memberRole) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if(member.getMemberRole() == MemberRole.ADMIN || memberRole == MemberRole.ADMIN) throw new RuntimeException("ADMIN의 역할 변경은 불가합니다.");
        member.updateRole(memberRole);
        return member.getId();
    }

    public List<MemberDetailResponse> getAll() {
        return memberRepository.findAll().stream().map(member->new MemberDetailResponse(member))
                .collect(Collectors.toList());
    }

    public List<MemberDetailResponse> getAllWithRole(MemberRole memberRole) {
        return memberRepository.findAllByMemberRole(memberRole).stream().map(member->new MemberDetailResponse(member))
                .collect(Collectors.toList());
    }
}
