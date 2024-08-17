package UniFest.domain.menu.controller;

import UniFest.domain.menu.entity.MenuStatus;
import UniFest.domain.menu.service.MenuService;
import UniFest.dto.request.menu.MenuStatusChangeRequest;
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

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "메뉴 삭제")
    @DeleteMapping("{menu-id}")
    public Response deleteMenu(@PathVariable("menu-id") Long menuId,
                             @AuthenticationPrincipal MemberDetails memberDetails) {
        menuService.deleteMenu(menuId, memberDetails);
        return Response.ofSuccess("OK",null);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "메뉴 재고 상태 변경")
    @PutMapping("{menu-id}/status")
    public Response changeMenuStatus(@Valid @RequestBody MenuStatusChangeRequest menuStatusChangeRequest,
                                     @AuthenticationPrincipal MemberDetails memberDetails) {
        Long menuId = menuStatusChangeRequest.getMenuId();
        MenuStatus menuStatus = menuStatusChangeRequest.getMenuStatus();
        menuService.changeMenuStatus(memberDetails ,menuId, menuStatus);

        return Response.ofSuccess("OK",null);
    }
}
