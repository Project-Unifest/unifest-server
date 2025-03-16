package UniFest.domain.booth.dto.response;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.entity.BoothCategory;
import lombok.Data;


@Data
public class LikedBoothResponse {
    private Long id;

    private String name;

    private BoothCategory category;

    private String description;

    private String thumbnail;

    private String location;

    private double latitude;

    private double longitude;
    private String warning;

    public LikedBoothResponse(Booth booth){
        this.id = booth.getId();
        this.name = booth.getName();
        this.category = booth.getCategory();
        this.description = booth.getDescription();
        this.thumbnail = booth.getThumbnail();
        this.location = booth.getLocation();
        this.latitude = booth.getLatitude();
        this.longitude = booth.getLongitude();
        this.warning = booth.getWarning();
    }
}
