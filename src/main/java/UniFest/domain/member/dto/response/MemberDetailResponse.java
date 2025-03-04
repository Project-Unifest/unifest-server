package UniFest.domain.member.dto.response;

import UniFest.domain.member.entity.Member;
import UniFest.domain.member.entity.MemberRole;
import UniFest.domain.booth.dto.response.BoothDetailResponse;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberDetailResponse {
    private final Long id;
    private final String email;
    private final List<BoothDetailResponse> booths;
    private final Long schoolId;
    private final String phoneNum;
    private final MemberRole memberRole;

    public MemberDetailResponse(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.booths = member.getBoothList().stream().map(BoothDetailResponse::new).collect(Collectors.toList());
        this.schoolId = member.getSchool().getId();
        this.phoneNum = member.getPhoneNum();
        this.memberRole = member.getMemberRole();
    }
}