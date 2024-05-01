package UniFest.dto.request.booth;

import UniFest.domain.booth.entity.BoothCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private boolean enabled;
    private float latitude;
    private float longitude;

}
