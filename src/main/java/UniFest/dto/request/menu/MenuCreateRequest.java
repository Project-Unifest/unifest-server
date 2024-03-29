package UniFest.dto.request.menu;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MenuCreateRequest {

    @NotBlank(message = "메뉴 이름을 입력해주세요.")
    private String name;

    @NotNull(message = "메뉴 가격을 입력해주세요.")
    private int price;

    private String imgUrl;

}
