package UniFest.domain.menu.dto.request;


import UniFest.domain.menu.entity.MenuStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MenuPatchRequest {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    @Length(max=20)
    @Schema(description = "메뉴 이름", nullable = false, maximum = "20")
    private String name;

    @NotNull(message = "메뉴 가격을 입력해주세요.")
    @Schema(description = "메뉴 가격", nullable = false)
    private int price;

    @Schema(description = "메뉴 사진", nullable = true)
    private String imgUrl;

    @Schema(description = "메뉴 재고 상태", example = "충분함 : ENOUGH(기본값), 50개 이하 : UNDER_50, 10개 이하 : UNDER_10, 품절 : SOLD_OUT", nullable = true)
    private MenuStatus menuStatus;

}
