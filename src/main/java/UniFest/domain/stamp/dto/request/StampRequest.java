package UniFest.domain.stamp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampRequest {

    @NotNull
    @Schema(name = "stamp를 찍을 deviceId", nullable = false)
    private String deviceId;

    @NotNull
    @Schema(name = "stamp를 찍을 BoothId", nullable = false)
    private Long boothId;

    @NotNull
    @Schema(name = "stamp를 찍을 FestivalId", nullable = false)
    private Long festivalId;
}
