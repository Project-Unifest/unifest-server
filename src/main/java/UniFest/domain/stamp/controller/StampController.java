package UniFest.domain.stamp.controller;

import UniFest.domain.booth.service.BoothService;
import UniFest.domain.stamp.service.StampService;
import UniFest.dto.request.stamp.StampRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.booth.BoothResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    public Response<Integer> addStamp(@RequestBody StampRequest stampRequest){
        int totalStamp = stampService.addStamp(stampRequest.getBoothId(), stampRequest.getToken());
        return Response.ofSuccess("OK", Integer.valueOf(totalStamp));
    }

    @Operation(summary = "Stamp 조회 (token별)")
    @GetMapping()
    public Response<Integer> getStamp(@RequestParam String token){
        return Response.ofSuccess("OK", Integer.valueOf(stampService.getStamp(token)));
    }

    @Operation(summary = "스탬프 기능 활성화 된 부스 확인")
    @GetMapping("/{festival-id}")
    public Response<List<BoothResponse>> getStampBooths(@PathVariable("festival-id") Long festivalId){
        List<BoothResponse> boothList = boothService.getStampEnabledBooths(festivalId);
        return Response.ofSuccess("OK", boothList);
    }

    @Operation(summary = "Booth StampEnabled 바꾸기(toggle)")
    @GetMapping("/{booth-id}/stampEnabled")
    public Response<Boolean> switchStampEnabled(@PathVariable("booth-id") Long boothId){
        return Response.ofSuccess("OK", boothService.updateStampEnabled(boothId));
    }
}