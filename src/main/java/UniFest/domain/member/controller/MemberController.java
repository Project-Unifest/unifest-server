package UniFest.domain.member.controller;

import UniFest.domain.member.entity.MemberRole;
import UniFest.domain.member.service.MemberService;
import UniFest.dto.request.member.MemberSignUpRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.member.MemberDetailResponse;
import UniFest.security.userdetails.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping
    public Response postMember(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        Long savedId = memberService.createMember(memberSignUpRequest);
        return Response.ofSuccess("OK", savedId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "회원역할 변경")
    @PatchMapping("{member-id}")
    public Response patchMemberRole(@PathVariable("member-id") Long memberId,
                                    @RequestParam("role")MemberRole memberRole) {
        Long updatedId = memberService.updateMemberRole(memberId,memberRole);
        return Response.ofSuccess("OK",updatedId);
    }

    @SecurityRequirement(name="JWT")
    @Operation(summary = "role 별 멤버 목록 조회 (parameter 생략 시 멤버 전부 조회)")
    @GetMapping
    public Response<List<MemberDetailResponse>> getMembers(
            @RequestParam(value = "role", required = false) MemberRole memberRole) {
        if (memberRole != null) {
            return Response.ofSuccess("OK", memberService.getAllWithRole(memberRole));
        }
        return Response.ofSuccess("OK", memberService.getAll());
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "본인 정보 조회")
    @GetMapping("my")
    public Response getMyMember(@AuthenticationPrincipal MemberDetails memberDetails){
        MemberDetailResponse response = memberService.getMember(memberDetails.getMemberId());
        return Response.ofSuccess("OK", response);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "member id로 회원 정보 조회")
    @GetMapping("{member-id}")
    public Response getMember(@PathVariable(value = "member-id") Long memberId) {
        MemberDetailResponse response = memberService.getMember(memberId);
        return Response.ofSuccess("OK", response);
    }
}