package UniFest.domain.stamp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StampInfoCreateRequest {

    @NotNull
    private Long festivalId;

    @NotNull
    private String defaultImgUrl;

    @NotNull
    private String usedImgUrl;
}
