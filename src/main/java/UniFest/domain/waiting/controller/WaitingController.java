package UniFest.domain.waiting.controller;

import UniFest.domain.booth.service.BoothService;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.waiting.entity.Waiting;
import UniFest.domain.waiting.service.WaitingService;
import UniFest.dto.request.waiting.CheckPinRequest;
import UniFest.dto.request.waiting.DeleteWaitingRequest;
import UniFest.dto.request.waiting.PostWaitingRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.waiting.WaitingInfo;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.security.userdetails.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/waiting")
public class WaitingController {

    private final BoothService boothService;
    private final WaitingService waitingService;
    private final BoothRepository boothRepository;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Pin 번호 받기")
    @GetMapping("/pin/{booth-id}")
    public Response<String> getPin(@PathVariable("booth-id") Long boothId,
                           @AuthenticationPrincipal MemberDetails memberDetails){
        String pin = boothService.getPin(memberDetails, boothId);

        return Response.ofSuccess("OK", pin);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Pin 발급/재발급")
    @PostMapping("/pin/{booth-id}")
    public Response<String> createPin(@PathVariable("booth-id") Long boothId,
                           @AuthenticationPrincipal MemberDetails memberDetails){
        String pin = boothService.createPin(memberDetails, boothId);

        return Response.ofSuccess("OK", pin);
    }

    @PostMapping("/pin/check")
    @Operation(summary = "Pin 번호 확인")
    public Response<Long> checkPin(@RequestBody CheckPinRequest checkPinRequest){
        Long boothId = checkPinRequest.getBoothId();
        String pin = checkPinRequest.getPinNumber();
        Booth existBooth = boothRepository.findByBoothId(boothId).filter(Booth::isEnabled)
                .orElseThrow(BoothNotFoundException::new);

        Long waitingCount = waitingService.getWaitingCountByBooth(existBooth, "RESERVED");
        String pinNumber = existBooth.getPin();
        if(!pinNumber.equals(pin)){
            return Response.ofFail("Pin 번호가 일치하지 않습니다", -1L);
        }

        return Response.ofSuccess("Pin 번호가 일치합니다", waitingCount);
    }



    @PostMapping
    @Operation(summary = "웨이팅 추가")
    public Response<WaitingInfo> addWaiting(@RequestBody PostWaitingRequest waitingRequest) {
        Booth existBooth = boothRepository.findByBoothId(waitingRequest.getBoothId())
                .filter(Booth::isEnabled)
                .orElseThrow(BoothNotFoundException::new);

        String pinNumber = existBooth.getPin();
        if(!pinNumber.equals(waitingRequest.getPinNumber())){
            return Response.ofFail("Pin 번호가 일치하지 않습니다", null);
        }

        Waiting newWaiting = new Waiting(
                existBooth,
                waitingRequest.getDeviceId(),
                waitingRequest.getTel(),
                waitingRequest.getPartySize()
        );
        WaitingInfo ret = waitingService.addWaiting(newWaiting);
        return Response.ofCreated("웨이팅이 추가되었습니다", ret);
    }
    @PutMapping
    @Operation(summary="사용자 측의 웨이팅 취소")
    public Response<WaitingInfo> cancelWaiting(@RequestBody DeleteWaitingRequest deleteWaitingRequest){
        String deviceId = deleteWaitingRequest.getDeviceId();
        Long waitingId = deleteWaitingRequest.getWaitingId();

        WaitingInfo ret = waitingService.cancelWaiting(deviceId, waitingId);
        if(ret == null){
            return Response.ofNotFound("대기열이 존재하지 않습니다", null);
        }
        return Response.ofSuccess("웨이팅을 취소했습니다", ret);
    }

    @GetMapping("/{boothId}/reserved")
    @Operation(summary = "예약된 대기열 조회")
    public Response<List<WaitingInfo>> getWaitingList(@PathVariable Long boothId) {
        return Response.ofSuccess("예약된 웨이팅을 조회합니다", waitingService.getWaitingList(boothId, Boolean.TRUE));
    }

    @GetMapping("/{boothId}/all")
    @Operation(summary = "완료 포함 전체 웨이팅 조회(관리자용, 디버깅용)")
    public Response<List<WaitingInfo>> getAllWaitingList(@PathVariable Long boothId) {
        return Response.ofSuccess("완료 포함 전체 웨이팅을 불러왔습니다", waitingService.getWaitingList(boothId, Boolean.FALSE));
    }

    @GetMapping("/{boothId}/count")
    @Operation(summary = "대기중인 팀의 수 조회")
    public Response<Long> getWaitingCount(@PathVariable Long boothId) {
        Long ret = waitingService.getWaitingCount(boothId, "RESERVED");
        if(ret == null){
            return Response.ofNotFound("대기중인 팀이 없습니다", 0L);
        }
        return Response.ofSuccess("데이터를 가져왔습니다", ret);
    }

    @GetMapping("/me/{deviceId}")
    @Operation(summary="내 RESERVED 웨이팅 조회(device ID 기준으로!)")
    public Response<List<WaitingInfo>> getMyWaitingList(@PathVariable String deviceId){
        List<WaitingInfo> ret = waitingService.getMyWaitingList(deviceId);
        if(ret.isEmpty()){
            return Response.ofNotFound("대기열이 존재하지 않습니다", null);
        }
        return Response.ofSuccess("데이터를 가져왔습니다", ret);
    }

    @PutMapping("/{waitingId}/call")
    @Operation(summary="관리자가 예약 호출")
    public Response<WaitingInfo> callWaiting(@PathVariable Long waitingId) {
        WaitingInfo ret =  waitingService.callWaiting(waitingId);
        if (ret == null) {
            return Response.ofNotFound("대기열이 존재하지 않습니다", null);
        }
        return Response.ofSuccess("호출했습니다", ret);
    }

    @PutMapping("/{waitingId}/complete")
    @Operation(summary="관리자의 입장 처리 완료")
    public Response<WaitingInfo> completeWaiting(@PathVariable Long waitingId) {
        WaitingInfo ret =  waitingService.completeWaiting(waitingId);
        if (ret == null) {
            return Response.ofNotFound("대기열이 존재하지 않습니다", null);
        }
        return Response.ofSuccess("완료했습니다", ret);
    }

    @PutMapping("/{waitingId}/noshow")
    @Operation(summary="관리자가 예약 부재 처리")
    public Response<WaitingInfo> noshowWaiting(@PathVariable Long waitingId) {
        WaitingInfo ret =  waitingService.setNoShow(waitingId);
        if (ret == null) {
            return Response.ofNotFound("대기열이 존재하지 않습니다", null);
        }
        return Response.ofSuccess("취소했습니다", ret);
    }

    @DeleteMapping("/{waitingId}")
    @Operation(summary = "관리자가 직접 웨이팅 삭제")
    public Response<WaitingInfo> removeWaiting(@PathVariable Long waitingId) {
        WaitingInfo waitingInfo =  waitingService.removeWaiting(waitingId);
        if(waitingInfo == null){
            return Response.ofNotFound("대기열이 존재하지 않습니다", null);
        }
        return Response.ofSuccess("대기열을 삭제했습니다", waitingInfo);
    }

    @PostMapping("/{boothId}/waitingEnabled")
    @Operation(summary = "부스 웨이팅 불/가 변경")
    public Response<Boolean> changeBoothWaitingEnabled(@PathVariable Long boothId){
        Boolean updatedVal = boothService.updateBoothWaitingEnabled(boothId);

        return Response.ofSuccess("OK", updatedVal);
    }

}