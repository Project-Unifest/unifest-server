package UniFest.global.infra.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;



@Getter
public class LoginRequest {

    @Email
    @NotNull(message = "이메일은 필수 값 입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수 값 입니다.")
    private String password;

}