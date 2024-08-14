package UniFest.dto.response.menu;

import UniFest.domain.menu.entity.Menu;
import UniFest.domain.menu.entity.MenuStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuResponse {

    private Long id;

    private String name;

    private int price;

    private String imgUrl;

    private MenuStatus menuStatus;

    public MenuResponse(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.imgUrl = menu.getImgUrl();
        this.menuStatus = menu.getMenuStatus();
    }
}
