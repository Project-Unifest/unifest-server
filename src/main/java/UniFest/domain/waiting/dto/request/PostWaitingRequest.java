package UniFest.domain.waiting.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostWaitingRequest {

    @NotNull(message = "boothId는 null이면 안됩니다.")
    @Schema(description = "등록하고자 하는 Booth Id", nullable = false)
    private Long boothId;

    @NotNull(message = "tel은 null이면 안됩니다.")
    @Schema(description = "전화번호", nullable = false)
    private String tel;

    @NotNull(message = "deviceId는 null이면 안됩니다.")
    @Schema(description = "등록자 deviceId", nullable = false)
    private String deviceId;

    @NotNull(message = "partySize는 null이면 안됩니다.")
    @Schema(description = "대기 인원", nullable = false)
    private int partySize;

    @NotNull(message = "pinNumber는 null이면 안됩니다.")
    @Schema(description = "pinNumber", nullable = false)
    private String pinNumber;

    @NotNull(message = "fcmToken은 null이면 안됩니다.")
    @Schema(description = "fcmToken", nullable = false)
    private String fcmToken;
}
