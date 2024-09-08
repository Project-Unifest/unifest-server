package UniFest.domain.stamp.controller;

import UniFest.domain.stamp.service.StampService;
import UniFest.dto.request.stamp.StampRequest;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stamps")
public class StampController {

    private final StampService stampService;

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
}
