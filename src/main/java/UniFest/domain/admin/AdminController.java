package UniFest.domain.admin;
import UniFest.global.common.response.Response;
import UniFest.domain.booth.dto.response.BoothDetailResponse;
import UniFest.domain.booth.dto.response.BoothResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "부스 주인 변경")
    @PostMapping("/booth/{booth_id}")
    public Response changeBoothOwner(@PathVariable("booth_id") Long boothId, @RequestParam("new") Long memberId) {
        adminService.changeBoothOwner(boothId, memberId);
        return Response.ofSuccess("OK", null);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "해당축제 부스 전체조회")
    @GetMapping("booth/{festival-id}/booths")
    public Response<List<BoothResponse>> getEveryBooths(@PathVariable("festival-id") Long festivalId) {
        List<BoothResponse> booths = adminService.getBoothsIncludingDisabled(festivalId);
        return Response.ofSuccess("OK", booths);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "특정부스 조회")
    @GetMapping("booth/{booth-id}")
    public Response<BoothDetailResponse> getBooth(@PathVariable("booth-id") Long boothId) {
        BoothDetailResponse findBooth = adminService.getBoothEvenDisabled(boothId);
        return Response.ofSuccess("OK", findBooth);
    }
}