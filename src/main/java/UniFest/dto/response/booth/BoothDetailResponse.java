package UniFest.dto.response.booth;

import UniFest.domain.booth.entity.Booth;
import UniFest.dto.response.menu.MenuResponse;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoothDetailResponse {

    private Long id;

    private String name;

    private String category;

    private String description;

    private String thumbnail;

    private String warning;

    //private int likes;

    private String location;

    private float latitude;

    private float longitude;

    private List<MenuResponse> menus;

    public BoothDetailResponse(Booth booth){
        this.id = booth.getId();
        this.name = booth.getName();
        this.category = booth.getCategory();
        this.description = booth.getDescription();
        this.thumbnail = booth.getThumbnail();
        this.warning = booth.getWarning();
        //this.likes = booth.getLikesList().size();
        this.location = booth.getLocation();
        this.latitude = booth.getLatitude();
        this.longitude = booth.getLongitude();
        this.menus = booth.getMenuList().stream().map(MenuResponse::new).collect(Collectors.toList());
    }

}
