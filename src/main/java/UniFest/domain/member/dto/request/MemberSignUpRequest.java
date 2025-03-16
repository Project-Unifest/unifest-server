package UniFest.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class MemberSignUpRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "공백일 수 없습니다.")
    private String password;

    @NotNull(message = "공백일 수 없습니다.")
    private Long schoolId;

    @NotBlank(message = "공백일 수 없습니다.")
    private String phoneNum;

}
