package UniFest.domain.member.controller;

import UniFest.domain.member.service.MemberService;
import UniFest.dto.request.member.MemberSignUpRequest;
import UniFest.dto.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
