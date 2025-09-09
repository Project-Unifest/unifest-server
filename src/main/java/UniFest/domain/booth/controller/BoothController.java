package UniFest.domain.booth.controller;

import UniFest.domain.booth.dto.response.GetAllBoothResponse;
import UniFest.domain.booth.service.BoothService;
import UniFest.domain.booth.dto.request.BoothCreateRequest;
import UniFest.domain.booth.dto.request.BoothPatchRequest;
import UniFest.domain.booth.dto.request.BoothSchedulePatchRequest;
import UniFest.global.common.response.Response;
import UniFest.domain.booth.dto.response.BoothDetailResponse;
import UniFest.domain.booth.dto.response.BoothResponse;
import UniFest.global.infra.security.userdetails.MemberDetails;
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
    public Response<Long> postBooth(@Valid @RequestBody BoothCreateRequest boothCreateRequest,
                              @AuthenticationPrincipal MemberDetails memberDetails) {
        Long savedId = boothService.createBooth(boothCreateRequest, memberDetails);
        return Response.ofCreated("OK",savedId);
    }


    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 정보 수정")
    @PatchMapping("/{booth-id}")
    public Response<Long> patchBooth(@RequestBody BoothPatchRequest boothPatchRequest,
                               @AuthenticationPrincipal MemberDetails memberDetails,
                               @PathVariable("booth-id") Long boothId) {
        Long updatedId = boothService.updateBooth(boothPatchRequest, memberDetails, boothId);
        return Response.ofSuccess("OK",updatedId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 스케쥴 수정")
    @PatchMapping("/{booth-id}/schedule")
    public Response<Long> patchBoothSchedule(@RequestBody BoothSchedulePatchRequest boothSchedulePatchRequest,
                               @AuthenticationPrincipal MemberDetails memberDetails,
                               @PathVariable("booth-id") Long boothId) {
        Long updatedId = boothService.updateBoothSchedule(boothId, boothSchedulePatchRequest);
        return Response.ofSuccess("OK",updatedId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 삭제")
    @DeleteMapping("/{booth-id}")
    public Response<Void> deleteBooth(@AuthenticationPrincipal MemberDetails memberDetails,
                                @PathVariable("booth-id") Long boothId) {
        boothService.deleteBoothWithAuth(memberDetails, boothId);
        return Response.ofSuccess("OK");
    }

    @Operation(summary = "특정부스 조회")
    @GetMapping("/{booth-id}")
    public Response<BoothDetailResponse> getBooth(@PathVariable("booth-id") Long boothId) {
        BoothDetailResponse findBooth = boothService.getBooth(boothId);
        return Response.ofSuccess("OK", findBooth);
    }

    @Operation(summary = "해당축제 영업 중인 부스 전체조회")
    @GetMapping("/{festival-id}/booths")
    public Response<GetAllBoothResponse> getBooths(@PathVariable("festival-id") Long festivalId) {
        return Response.ofSuccess("OK", boothService.getBooths(festivalId));
    }

    @Operation(summary = "상위 5개 부스 확인")
    @GetMapping
    public Response<List<BoothResponse>> getLikes(@RequestParam Long festivalId){
        List<BoothResponse> boothList = boothService.getTrendingBooths(festivalId);
        return Response.ofSuccess("OK", boothList);
    }
}
