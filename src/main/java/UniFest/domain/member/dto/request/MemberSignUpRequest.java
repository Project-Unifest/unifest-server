package UniFest.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class MemberSignUpRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(format = "email", nullable = false)
    private String email;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "비밀번호", nullable = false)
    private String password;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "학교Id", nullable = false)
    private Long schoolId;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "전화번호", nullable = false)
    private String phoneNum;

}
