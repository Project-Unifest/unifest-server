package UniFest.domain.waiting.controller;

import UniFest.domain.booth.service.BoothService;
import UniFest.dto.request.booth.BoothPatchRequest;
import UniFest.dto.response.Response;
import UniFest.security.userdetails.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/waiting")
public class WaitingController {

    private final BoothService boothService;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Pin 번호 받기")
    @GetMapping("/pin/{booth-id}")
    public Response getPin(@PathVariable("booth-id") Long boothId,
                           @AuthenticationPrincipal MemberDetails memberDetails){
        String pin = boothService.getPin(memberDetails, boothId);

        return Response.ofSuccess("OK", pin);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Pin 발급/재발급")
    @PostMapping("/pin/{booth-id}")
    public Response createPin(@PathVariable("booth-id") Long boothId,
                           @AuthenticationPrincipal MemberDetails memberDetails){
        String pin = boothService.createPin(memberDetails, boothId);

        return Response.ofSuccess("OK", pin);
    }

}
