package UniFest.dto.response.menu;

import UniFest.domain.menu.entity.Menu;
import lombok.Data;

@Data
public class MenuResponse {

    private Long id;

    private String name;

    private int price;

    private String imgUrl;

    public MenuResponse(Menu menu){
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.imgUrl = menu.getImgUrl();
    }
}
