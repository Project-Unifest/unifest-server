package UniFest.domain.megaphone.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMegaphoneRequest {
    @NotNull(message = "boothId는 null이면 안됩니다.")
    @Schema(description = "부스 Id", nullable = false)
    private Long boothId;

    @NotNull(message = "msgBody는 null이면 안됩니다.")
    @Schema(description = "전송할 메세지", nullable = false)
    private String msgBody;
}
