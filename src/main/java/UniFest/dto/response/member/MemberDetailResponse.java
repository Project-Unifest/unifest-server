package UniFest.dto.response.member;

import UniFest.domain.member.entity.Member;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.security.userdetails.MemberDetails;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberDetailResponse {
    private String email;
    private List<BoothResponse> booths;
    private Long schoolId;
    private String phoneNum;

    public MemberDetailResponse(Member member){
        this.email = member.getEmail();
        this.booths = member.getBoothList().stream().map(BoothResponse::new).collect(Collectors.toList());
        this.schoolId = member.getSchoolId();
        this.phoneNum = member.getPhoneNum();
    }
}