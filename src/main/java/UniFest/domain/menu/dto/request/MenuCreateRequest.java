package UniFest.domain.menu.dto.request;


import UniFest.domain.menu.entity.MenuStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MenuCreateRequest {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    @Length(max=70) //기존 20자, 50자 늘림
    @Schema(description = "메뉴의 이름 (최대 70자)", nullable = false)
    private String name;

    @NotNull(message = "메뉴 가격을 입력해주세요.")
    @Schema(description = "부스의 경고문(최대 100자)", nullable = false)
    private int price;

    @Schema(description = "이미지", nullable = true)
    private String imgUrl;

    @Schema(description = "메뉴 재고 상태", example = "충분함 : ENOUGH(기본값) / 50개 이하 : UNDER_50 / 10개 이하 : UNDER_10 / 품절 : SOLD_OUT")
    private MenuStatus menuStatus;
}
