package UniFest.domain.stamp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StampEnabledRequest{
    @NotNull
    private Boolean stampEnabled;
}
