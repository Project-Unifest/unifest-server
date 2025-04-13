package UniFest.domain.festival.controller;

import UniFest.domain.festival.service.InterestService;
import UniFest.domain.festival.dto.request.InterestRequest;
import UniFest.global.common.response.ErrorResponse;
import UniFest.global.common.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(summary = "관심 축제 등록",
            description = "사용자가 특정 축제에 대해 관심을 등록합니다. 관심 등록 시 해당 축제에 대한 푸시 알림 구독이 추가됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관심 축제 등록에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "이미 이 디바이스로 관심 등록한 축제입니다. (InterestAlreadyExistsException) [에러 코드: 4002]",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "해당 축제가 존재하지 않습니다. (FestivalNotFoundException) [에러 코드: 4001]",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @PostMapping("/{festival-id}/interest")
    public Response<Void> postInterest(@PathVariable("festival-id") Long festivalId,
                                       @RequestBody InterestRequest interestRequest) {
        interestService.addFestivalInterest(interestRequest.getDeviceId(), festivalId);
        return Response.ofSuccess("관심 축제 등록에 성공했습니다.");
    }

    @Operation(summary = "관심 축제 해제",
            description = "사용자가 특정 축제에 대한 관심을 해제합니다. 해제 시 해당 축제에 대한 푸시 알림 구독이 취소됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관심 축제 해제에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 디바이스의 해당 축제에 대한 관심이 존재하지 않습니다. (InterestNotFoundException) [에러 코드: 4003]",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "해당 축제가 존재하지 않습니다. (FestivalNotFoundException) [에러 코드: 4001]",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    @DeleteMapping("/{festival-id}/interest")
    public Response<Void> deleteInterest(@PathVariable("festival-id") Long festivalId,
                                         @RequestBody InterestRequest interestRequest) {
        interestService.deleteFestivalInterest(interestRequest.getDeviceId(), festivalId);
        return Response.ofSuccess("관심 축제 해제에 성공했습니다.");
    }

    @Operation(summary = "관심 축제 목록 조회",
            description = "사용자가 등록한 모든 관심 축제의 ID 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관심 축제 목록 조회에 성공했습니다.")
    })
    @GetMapping("/interest")
    public Response<List<Long>> getInterestList(@RequestParam("deviceId") String deviceId) {
        List<Long> interestedFestivalIds = interestService.getInterestedFestivalIds(deviceId);
        return Response.ofSuccess("관심 축제 목록 조회에 성공했습니다.", interestedFestivalIds);
    }
}
