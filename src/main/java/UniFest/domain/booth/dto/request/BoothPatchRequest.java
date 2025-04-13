package UniFest.domain.booth.dto.request;

import UniFest.domain.booth.entity.BoothCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "부스 수정 요청, 수정할 부분만 기입")
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
