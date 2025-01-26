package UniFest.domain.festival.controller;

import UniFest.domain.festival.service.InterestService;
import UniFest.dto.request.festival.InterestRequest;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/festival")
public class InterestController {

    private final InterestService interestService;

    @Operation(summary = "관심 축제 등록")
    @PostMapping("/{festival-id}/interest")
    public Response postInterest(@RequestBody InterestRequest interestRequest) {
        interestService.addFestivalInterest(interestRequest.getDeviceId(), interestRequest.getFestivalId());
        return Response.ofSuccess("관심 축제 등록에 성공했습니다.", null);
    }

    @Operation(summary = "관심 축제 해제")
    @DeleteMapping("/{festival-id}/interest")
    public Response deleteInterest(@RequestBody InterestRequest interestRequest) {
        interestService.deleteFestivalInterest(interestRequest.getDeviceId(), interestRequest.getFestivalId());
        return Response.ofSuccess("관심 축제 해제에 성공했습니다.", null);
    }
}
