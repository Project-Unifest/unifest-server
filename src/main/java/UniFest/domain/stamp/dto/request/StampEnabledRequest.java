package UniFest.domain.stamp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampEnabledRequest{
    @NotNull
    @Schema(description = "허용 여부", nullable = false)
    private Boolean stampEnabled;
}
