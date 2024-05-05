package UniFest.dto.request.booth;

import UniFest.domain.booth.entity.BoothCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
public class BoothCreateRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    private String name;
    @NotNull(message = "공백일 수 없습니다.")
    private BoothCategory category;
    private String description;
    private String detail;
    private String thumbnail;
    private String warning;
    private Long festivalId;
    @NotNull(message = "공백일 수 없습니다.")
    private String location;
    @NotNull(message = "공백일 수 없습니다.")
    private double latitude;
    @NotNull(message = "공백일 수 없습니다.")
    private double longitude;

}
