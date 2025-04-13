package UniFest.domain.waiting.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CancelWaitingRequest {

    @NotNull(message = "waitingId은 null이면 안됩니다.")
    @Schema(description = "대기정보 Id", nullable = false)
    private Long waitingId;

    @NotNull(message = "deviceId은 null이면 안됩니다.")
    @Schema(description = "대기자 deviceId", nullable = false)
    private String deviceId;
}
