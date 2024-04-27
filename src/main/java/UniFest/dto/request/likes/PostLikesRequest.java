package UniFest.dto.request.likes;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostLikesRequest {
    @NotBlank(message="공백일 수 없습니다")
    private Long boothId;

    @NotBlank(message="공백일 수 없습니다")
    private String token;
}
