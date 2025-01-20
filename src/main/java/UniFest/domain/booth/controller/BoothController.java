package UniFest.domain.booth.controller;

import UniFest.domain.booth.service.BoothService;
import UniFest.dto.request.booth.BoothCreateRequest;
import UniFest.dto.request.booth.BoothPatchRequest;
import UniFest.dto.request.booth.BoothSchedulePatchRequest;
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
        return Response.ofCreated("OK",savedId);
    }


    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 정보 수정")
    @PatchMapping("/{booth-id}")
    public Response patchBooth(@RequestBody BoothPatchRequest boothPatchRequest,
                               @AuthenticationPrincipal MemberDetails memberDetails,
                               @PathVariable("booth-id") Long boothId) {
        Long updatedId = boothService.updateBooth(boothPatchRequest, memberDetails, boothId);
        return Response.ofSuccess("OK",updatedId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 스케쥴 수정")
    @PatchMapping("/{booth-id}/schedule")
    public Response patchBoothSchedule(@RequestBody BoothSchedulePatchRequest boothSchedulePatchRequest,
                               @AuthenticationPrincipal MemberDetails memberDetails,
                               @PathVariable("booth-id") Long boothId) {
        Long updatedId = boothService.updateBoothSchedule(boothId, boothSchedulePatchRequest);
        return Response.ofSuccess("OK",updatedId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 삭제")
    @DeleteMapping("/{booth-id}")
    public Response deleteBooth(@AuthenticationPrincipal MemberDetails memberDetails,
                                @PathVariable("booth-id") Long boothId) {
        boothService.deleteBoothWithAuth(memberDetails, boothId);
        return Response.ofSuccess("OK",null);
    }

    @Operation(summary = "특정부스 조회")
    @GetMapping("/{booth-id}")
    public Response getBooth(@PathVariable("booth-id") Long boothId) {
        BoothDetailResponse findBooth = boothService.getBooth(boothId);
        return Response.ofSuccess("OK", findBooth);
    }

    @Operation(summary = "해당축제 영업 중인 부스 전체조회")
    @GetMapping("/{festival-id}/booths")
    public Response getBooths(@PathVariable("festival-id") Long festivalId) {
        List<BoothResponse> booths = boothService.getBooths(festivalId);
        return Response.ofSuccess("OK", booths);
    }

    @Operation(summary = "상위 5개 부스 확인")
    @GetMapping
    public Response<List<BoothResponse>> getLikes(@RequestParam Long festivalId){
        List<BoothResponse> boothList = boothService.getTrendingBooths(festivalId);
        return Response.ofSuccess("OK", boothList);
    }
}
