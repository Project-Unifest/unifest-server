package UniFest.domain.waiting.controller;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.waiting.entity.Waiting;
import UniFest.domain.waiting.service.WaitingService;
import UniFest.dto.request.waiting.PostWaitingRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.waiting.WaitingInfo;
import UniFest.exception.booth.BoothNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waiting")
@RequiredArgsConstructor
public class WaitingController {
    private final WaitingService waitingService;
    private final BoothRepository boothRepository;

    @PostMapping
    @Operation(summary = "대기열 추가")
    public Response<WaitingInfo> addWaiting(@RequestBody PostWaitingRequest waitingRequest) {
        Booth existBooth = boothRepository.findByBoothId(waitingRequest.getBoothId())
                .filter(Booth::isEnabled)
                .orElseThrow(BoothNotFoundException::new);

        Waiting newWaiting = new Waiting(
                existBooth,
                waitingRequest.getDeviceId(),
                waitingRequest.getTel(),
                waitingRequest.getPartySize()
        );
        Waiting createdWaiting = waitingService.addWaiting(newWaiting);
        WaitingInfo ret = new WaitingInfo(
                createdWaiting.getBooth().getId(),
                createdWaiting.getId(),
                createdWaiting.getPartySize(),
                createdWaiting.getTel(),
                createdWaiting.getDeviceId(),
                createdWaiting.getCreatedAt(),
                createdWaiting.getUpdatedAt(),
                createdWaiting.getStatus()
        );
        System.out.println("ret = " + ret);
        return Response.ofCreated("created", ret);
    }

    @GetMapping("/{boothId}/reserved")
    @Operation(summary = "예약된 대기열 조회")
    public Response<List<WaitingInfo>> getWaitingList(@PathVariable Long boothId) {
        return Response.ofSuccess("data", waitingService.getWaitingList(boothId));
    }

    @GetMapping("/{boothId}/all")
    @Operation(summary = "완료 포함 전체 대기열 조회")
    public Response<List<WaitingInfo>> getAllWaitingList(@PathVariable Long boothId) {
        return Response.ofSuccess("data", waitingService.getAllWaitingList(boothId));
    }
    @GetMapping("/{boothId}/pop")
    @Operation(summary = "대기열에서 가장 높은 우선순위의 대기열을 꺼냄")
    public Response<WaitingInfo> popHighestPriorityWaiting(@PathVariable Long boothId, @RequestParam int partySize) {
        WaitingInfo ret = waitingService.popHighestPriorityWaiting(boothId, partySize);
        return Response.ofSuccess("data", ret);
    }

    @DeleteMapping("/{boothId}/{id}")
    @Operation(summary = "대기열 삭제")
    public void removeWaiting(@PathVariable Long boothId, @PathVariable Long id) {
        waitingService.removeWaiting(boothId, id);
    }

    @GetMapping("/{boothId}/count")
    @Operation(summary = "대기열 수 조회")
    public Response<Long> getWaitingCount(@PathVariable Long boothId) {
        return Response.ofSuccess("data", waitingService.getWaitingCount(boothId));
    }
}