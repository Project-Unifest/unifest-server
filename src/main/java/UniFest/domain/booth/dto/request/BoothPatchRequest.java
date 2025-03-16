package UniFest.domain.booth.dto.request;

import UniFest.domain.booth.entity.BoothCategory;
import lombok.Data;


@Data
public class BoothPatchRequest {

    private String name;
    private BoothCategory category;
    private String description;
    private String detail;
    private String thumbnail;
    private String warning;
    private String location;
    private Boolean enabled;
    private Double latitude;
    private Double longitude;

    private Boolean waitingEnabled;
}
