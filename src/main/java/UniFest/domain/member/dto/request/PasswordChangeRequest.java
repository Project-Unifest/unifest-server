package UniFest.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class PasswordChangeRequest {
    @NotBlank(message = "공백일 수 없습니다.")
    private String currentPassword;

    @NotBlank(message = "공백일 수 없습니다.")
    private String newPassword;
}
