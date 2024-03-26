package UniFest.dto.response.booth;

import UniFest.domain.booth.entity.Booth;
import lombok.Data;

@Data
public class BoothResponse {

    private Long id;

    private String name;

    private String category;

    private String description;

    private String thumbnail;

    private String warning;

    private int likes;

    private float latitude;

    private float longtitude;


    public BoothResponse(Booth booth){
        this.id = booth.getId();
        this.name = booth.getName();
        this.category = booth.getCategory();
        this.description = booth.getDescription();
        this.thumbnail = booth.getThumbnail();
        this.warning = booth.getWarning();
        this.likes = booth.getLikesList().size();
        this.latitude = booth.getLatitude();
        this.longtitude = booth.getLongtitude();
    }
}
