package UniFest.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class PasswordChangeRequest {
    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "현재 비밀번호", nullable = false)
    private String currentPassword;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "변경할 비밀번호", nullable = false)
    private String newPassword;
}
