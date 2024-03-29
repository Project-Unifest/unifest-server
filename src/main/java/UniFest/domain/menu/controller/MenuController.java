package UniFest.domain.menu.controller;

import UniFest.domain.menu.service.MenuService;
import UniFest.dto.request.booth.BoothCreateRequest;
import UniFest.dto.request.menu.MenuCreateRequest;
import UniFest.dto.response.Response;
import UniFest.security.userdetails.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;
    //메뉴 등록
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "메뉴 등록")
    @PostMapping("{booth-id}")
    public Response postMenu(@Valid @RequestBody MenuCreateRequest menuCreateRequest,
                              @PathVariable("booth-id") Long boothId,
                              @AuthenticationPrincipal MemberDetails memberDetails) {
        Long savedId = menuService.postMenu(menuCreateRequest,boothId, memberDetails);
        return Response.ofSuccess("OK",savedId);
    }
}
