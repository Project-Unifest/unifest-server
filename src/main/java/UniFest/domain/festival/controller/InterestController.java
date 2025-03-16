package UniFest.domain.festival.controller;

import UniFest.domain.festival.service.InterestService;
import UniFest.domain.festival.dto.request.InterestRequest;
import UniFest.global.common.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/festival")
public class InterestController {

    private final InterestService interestService;

    @Operation(summary = "관심 축제 등록")
    @PostMapping("/{festival-id}/interest")
    public Response postInterest(@PathVariable("festival-id") Long festivalId,
                                 @RequestBody InterestRequest interestRequest) {
        interestService.addFestivalInterest(interestRequest.getDeviceId(), festivalId);
        return Response.ofSuccess("관심 축제 등록에 성공했습니다.", null);
    }

    @Operation(summary = "관심 축제 해제")
    @DeleteMapping("/{festival-id}/interest")
    public Response deleteInterest(@PathVariable("festival-id") Long festivalId,
                                   @RequestBody InterestRequest interestRequest) {
        interestService.deleteFestivalInterest(interestRequest.getDeviceId(), festivalId);
        return Response.ofSuccess("관심 축제 해제에 성공했습니다.", null);
    }

    @Operation(summary = "관심 축제 목록 조회")
    @GetMapping("/interest")
    public Response getInterestList(@RequestParam("deviceId") String deviceId) {
        List<Long> interestedFestivalIds = interestService.getInterestedFestivalIds(deviceId);
        return Response.ofSuccess("관심 축제 목록 조회 성공", interestedFestivalIds);
    }

}
