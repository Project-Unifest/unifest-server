package UniFest.domain.waiting.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckPinRequest {

    @NotNull(message = "boothId은 null이면 안됩니다.")
    @Schema(description = "확인하고자 하는 BoothId", nullable = false)
    private Long boothId;

    @NotNull(message = "pinNumber는 null이면 안됩니다.")
    @Schema(description = "pinNumber", nullable = false)
    private String pinNumber;
}
