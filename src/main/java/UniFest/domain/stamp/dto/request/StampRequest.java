package UniFest.domain.stamp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampRequest {

    @NotNull
    private String deviceId;

    @NotNull
    private Long boothId;

    @NotNull
    private Long festivalId;
}
