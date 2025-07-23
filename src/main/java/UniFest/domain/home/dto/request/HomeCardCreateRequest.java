package UniFest.domain.home.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class HomeCardCreateRequest {
    @NotNull
    @Schema(description = "썸네일로 사용할 이미지", nullable = false)
    private String thumbnailImgUrl;

    @NotNull
    @Schema(description = "자세히보기 원본 이미지", nullable = false)
    private String detailImgUrl;
}
