package UniFest.domain.festival.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InterestRequest {

    @NotNull(message = "deviceId는 null이면 안됩니다.")
    @Schema(description = "deviceId", nullable = false)
    private String deviceId;
}
