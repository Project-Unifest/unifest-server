package UniFest.domain.member.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping
    public Response postMember(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        Long savedId = memberService.createMember(memberSignUpRequest);
        return Response.ofSuccess("OK",savedId);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("my")
    public Response getMyMemberInfo(@AuthenticationPrincipal MemberDetails memberDetails){
        MemberDetailResponse response = memberService.getMember(memberDetails.getMemberId());
        return Response.ofSuccess("OK", response);
    }
}
