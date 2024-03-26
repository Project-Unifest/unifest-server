package UniFest.domain.booth.controller;

import UniFest.domain.booth.service.BoothService;
import UniFest.dto.request.booth.BoothCreateRequest;
import UniFest.dto.response.Response;
import UniFest.security.userdetails.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booths")
public class BoothController {

    private final BoothService boothService;
    //부스 등록
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 생성")
    @PostMapping
    public Response postBooth(@Valid @RequestBody BoothCreateRequest boothCreateRequest,
                              @AuthenticationPrincipal MemberDetails memberDetails) {
        Long savedId = boothService.createBooth(boothCreateRequest, memberDetails);
        return Response.ofSuccess("OK",savedId);
    }
    //부스 조회
//    @Operation(summary = "부스 조회")
//    @GetMapping("/{booth-id}")
//    public Response getBooth(@PathVariable("course-id") Long boothId) {
//        BoothResponse findBooth = boothService.getBooth(boothId);
//        return Response.ofSuccess("OK", findBooth);
//    }

}
