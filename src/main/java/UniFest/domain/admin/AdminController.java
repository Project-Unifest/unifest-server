package UniFest.domain.admin;

import UniFest.domain.member.entity.MemberRole;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
}