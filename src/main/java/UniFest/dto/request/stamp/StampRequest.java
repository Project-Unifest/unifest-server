package UniFest.dto.request.stamp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampRequest {

    @NotNull
    private String token;

    @NotNull
    private Long boothId;
}
