package UniFest.domain.school.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostSchoolRequest {

    @NotNull(message = "name은 null이면 안됩니다.")
    @Schema(description = "현재 비밀번호", nullable = false)
    private String name;

    @NotNull(message = "region은 null이면 안됩니다.")
    @Schema(description = "지역", nullable = false)
    private String region;

    @NotNull(message = "address은 null이면 안됩니다.")
    @Schema(description = "주소", nullable = false)
    private String address;

    @NotNull(message = "latitude은 null이면 안됩니다.")
    @Schema(description = "위도", nullable = false)
    private double latitude;

    @NotNull(message = "longtitud은 null이면 안됩니다.")
    @Schema(description = "경도", nullable = false)
    private double longitude;

    @NotNull(message = "thumnail은 null이면 안됩니다.")
    @Schema(description = "학교 썸네일", nullable = false)
    private String thumbnail;
}
