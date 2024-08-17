package UniFest.dto.request.menu;

import UniFest.domain.menu.entity.MenuStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MenuStatusChangeRequest {

    private Long menuId;

    @Schema(description = "메뉴 재고 상태", example = "충분함 : ENOUGH(기본값), 50개 이하 : UNDER_50, 10개 이하 : UNDER_10, 품절 : SOLD_OUT")
    private MenuStatus menuStatus;
}
