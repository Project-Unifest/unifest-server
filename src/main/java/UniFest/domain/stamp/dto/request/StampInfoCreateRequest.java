package UniFest.domain.stamp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StampInfoCreateRequest {

    @NotNull
    @Schema(description = "페스티벌 ID", nullable = false)
    private Long festivalId;

    @NotNull
    @Schema(description = "stamp를 찍기 전 이미지", nullable = false)
    private String defaultImgUrl;

    @NotNull
    @Schema(description = "stamp를 찍은 후 이미지", nullable = false)
    private String usedImgUrl;
}
