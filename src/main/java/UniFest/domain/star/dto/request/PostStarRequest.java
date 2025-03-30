package UniFest.domain.star.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostStarRequest {
    @NotNull(message = "name은 null이면 안됩니다.")
    @Schema(description = "공연 출연자 이름", nullable = false)
    private String name;

    @NotNull(message = "imgUrl은 null이면 안됩니다.")
    @Schema(description = "공연 출연자 사진", nullable = false)
    private String imgUrl;
}
