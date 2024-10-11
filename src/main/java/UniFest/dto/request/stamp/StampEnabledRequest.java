package UniFest.dto.request.stamp;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampEnabledRequest{
    @NotNull
    private Boolean stampEnabled;
}
