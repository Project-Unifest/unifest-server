package UniFest.dto.response.member;

import UniFest.domain.member.entity.Member;
import UniFest.domain.member.entity.MemberRole;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.security.userdetails.MemberDetails;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberDetailResponse {
    private Long id;
    private String email;
    private List<BoothResponse> booths;
    private Long schoolId;
    private String phoneNum;
    private MemberRole memberRole;

    public MemberDetailResponse(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.booths = member.getBoothList().stream().map(BoothResponse::new).collect(Collectors.toList());
        this.schoolId = member.getSchool().getId();
        this.phoneNum = member.getPhoneNum();
        this.memberRole = member.getMemberRole();
    }
}