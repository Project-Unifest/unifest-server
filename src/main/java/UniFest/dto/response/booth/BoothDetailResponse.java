package UniFest.dto.response.booth;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.entity.BoothCategory;
import UniFest.dto.response.menu.MenuResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoothDetailResponse {

    private Long id;

    private String name;

    private BoothCategory category;

    private String description;

    private String thumbnail;

    private String warning;

    private String location;

    private double latitude;

    private double longitude;

    private List<MenuResponse> menus;

    private int likes;

    public BoothDetailResponse(Booth booth){
        this.id = booth.getId();
        this.name = booth.getName();
        this.category = booth.getCategory();
        this.description = booth.getDescription();
        this.thumbnail = booth.getThumbnail();
        this.warning = booth.getWarning();
        this.location = booth.getLocation();
        this.latitude = booth.getLatitude();
        this.longitude = booth.getLongitude();
        this.menus = booth.getMenuList().stream().map(MenuResponse::new).collect(Collectors.toList());
        this.likes = booth.getLikesCount();
    }

}
