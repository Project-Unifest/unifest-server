package UniFest.domain.stamp.controller;

import UniFest.domain.booth.service.BoothService;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.stamp.service.StampService;
import UniFest.dto.request.stamp.StampEnabledRequest;
import UniFest.dto.request.stamp.StampInfoCreateRequest;
import UniFest.dto.request.stamp.StampRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.dto.response.stamp.StampEnabledFestival;
import UniFest.dto.response.stamp.StampInfoResponse;
import UniFest.dto.response.stamp.StampRecordResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stamps")
public class StampController {

    private final StampService stampService;
    private final BoothService boothService;

    @Operation(summary = "Stamp 추가")
    @PostMapping()
    public Response<Long> addStamp(@RequestBody StampRequest stampRequest){
        Long stampRecordId = stampService.addStamp(stampRequest.getBoothId(), stampRequest.getDeviceId());
        return Response.ofSuccess("OK", stampRecordId);
    }

    @Operation(summary = "Stamp 조회 (deviceId별)")
    @GetMapping()
    public Response<List<StampRecordResponse>> getStamp(@RequestParam String deviceId){
        return Response.ofSuccess("OK", stampService.getStamp(deviceId));
    }

//    @Operation(summary = "Stamp 기능이 있는 부스 조회 (festivalId별)")
//    @GetMapping("/festival")
//    public Response<List<StampInfoResponse>> getStampByFestival(@PathVariable Long festivalId){
//        return Response.ofSuccess("OK", stampService.getStampInfo(festivalId));
//    }

    @Operation(summary = "Stamp 기능이 있는 부스 조회 (festivalId별)")
    @GetMapping("/{festival-id}")
    public Response<List<BoothResponse>> getStampBooths(@PathVariable("festival-id") Long festivalId){
//        List<BoothResponse> boothList = boothService.getStampEnabledBooths(festivalId);
        List<BoothResponse> stampBoothList = stampService.getStampBooth(festivalId);
        return Response.ofSuccess("OK", stampBoothList);
    }

    @Operation(summary = "Stamp 기능이 있는 페스티벌 조회 (드롭다운용)")
    @GetMapping("/festivals")
    public Response<List<StampEnabledFestival>> getStampFestivals(){    //나중에 Response로 교체하기 TODO
//        List<BoothResponse> boothList = boothService.getStampEnabledBooths(festivalId);
        List<StampEnabledFestival> stampEnabledFestival = stampService.getStampEnabledFestival();
        return Response.ofSuccess("OK", stampEnabledFestival);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "페스티벌 스탬프 정보 생성")
    @PostMapping("/festival")
    public Response<Long> createStampInfo(@RequestBody StampInfoCreateRequest stampInfoCreateRequest){
        Long stampInfoId = stampService.createStampInfo(stampInfoCreateRequest);
        return Response.ofSuccess("OK", stampInfoId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "페스티벌 스탬프 정보 삭제")
    @DeleteMapping("/festival/{festival-id}")
    public Response<Long> deleteStampInfo(@PathVariable("festival-id")Long festivalId){
        Long stampInfoId = stampService.deleteStampInfo(festivalId);
        return Response.ofSuccess("OK", stampInfoId);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Booth StampEnabled 바꾸기")
    @PatchMapping("/{booth-id}/stampEnabled")
    public Response<Boolean> switchStampEnabled(@PathVariable("booth-id") Long boothId,
                                                    @RequestBody StampEnabledRequest stampEnabledRequest){
        return Response.ofSuccess("OK", boothService.updateStampEnabled(boothId, stampEnabledRequest));
    }
}
