package UniFest.domain.waiting.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostTokenTestRequest {

    @NotNull(message = "fcmToken은 null이면 안됩니다.")
    @Schema(description = "fcmToken", nullable = false)
    private String fcmToken;
}
