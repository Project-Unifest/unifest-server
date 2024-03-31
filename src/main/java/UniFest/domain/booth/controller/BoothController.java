package UniFest.domain.booth.controller;

import UniFest.domain.booth.service.BoothService;
import UniFest.dto.request.booth.BoothCreateRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.booth.BoothDetailResponse;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.security.userdetails.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
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

    @Operation(summary = "특정부스 조회")
    @GetMapping("/{booth-id}")
    public Response getBooth(@PathVariable("booth-id") Long boothId) {
        log.info("[특정 부스 조회]");
        BoothDetailResponse findBooth = boothService.getBooth(boothId);
        return Response.ofSuccess("OK", findBooth);
    }

    @Operation(summary = "해당축제 부스 전체조회")
    @GetMapping("/{festival-id}/booths")
    public Response getBooths(@PathVariable("festival-id") Long festivalId) {
        List<BoothResponse> booths = boothService.getBooths(festivalId);
        return Response.ofSuccess("OK", booths);
    }

    @Operation(summary = "상위 5개 부스 확인")
    @GetMapping
    public Response<List<BoothDetailResponse>> getLikes(@RequestParam Long festivalId){
        List<BoothDetailResponse> boothList = boothService.getTrendingBooths(festivalId);
        return Response.ofSuccess("OK", boothList);
    }
}
