package UniFest.global.infra.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;



@Getter
public class LoginRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotNull(message = "이메일은 필수 값 입니다.")
    @Schema(format = "email", nullable = false)
    private String email;

    @NotNull(message = "비밀번호는 필수 값 입니다.")
    @Schema(nullable = false)
    private String password;

}