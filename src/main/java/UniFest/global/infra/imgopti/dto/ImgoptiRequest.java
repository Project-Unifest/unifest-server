package UniFest.global.infra.imgopti.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImgoptiRequest {
    @NotNull(message="preImgURL은 null이면 안됩니다.")
    @Schema(nullable = false)
    private String preImgUrl;

    @NotNull(message="postImgURL은 null이면 안됩니다.")
    @Schema(nullable = false)
    private String postImgUrl;
}
