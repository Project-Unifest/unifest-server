package UniFest.domain.stamp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StampInfoUpdateRequest {

    @NotNull
    @Schema(description = "수정할 stmampInfo Id", nullable = false)
    private Long stampInfoId;

    @Schema(description = "수정할 stamp를 찍기 전 이미지", nullable = true)
    private String defaultImgUrl;

    @Schema(description = "수정할 stamp를 찍은 후 이미지", nullable = true)
    private String usedImgUrl;
}
