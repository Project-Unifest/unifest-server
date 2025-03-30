package UniFest.domain.menu.dto.request;

import UniFest.domain.menu.entity.MenuStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuStatusChangeRequest {
    @NotNull(message = "메뉴 재고 상태는 공란이면 안됩니다.")
    @Schema(description = "메뉴 재고 상태", example = "충분함 : ENOUGH(기본값), 50개 이하 : UNDER_50, 10개 이하 : UNDER_10, 품절 : SOLD_OUT", nullable = false)
    private MenuStatus menuStatus;
}
