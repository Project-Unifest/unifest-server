package UniFest.domain.member.controller;

import UniFest.domain.member.service.MemberService;
import UniFest.dto.request.member.MemberSignUpRequest;
import UniFest.dto.response.Response;
import UniFest.security.userdetails.MemberDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public Response postMember(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        Long savedId = memberService.createMember(memberSignUpRequest);
        return Response.ofSuccess("OK",savedId);
    }

    @GetMapping("info")
    public String testMember(@AuthenticationPrincipal MemberDetails memberDetails){
        return memberDetails.getEmail() + "\n" + memberDetails.getRole() +
                "\n" + memberDetails.getUsername() + "\n" + memberDetails.getAuthorities();
    }
}
