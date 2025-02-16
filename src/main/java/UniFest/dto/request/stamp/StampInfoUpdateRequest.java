package UniFest.dto.request.stamp;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StampInfoUpdateRequest {

    @NotNull
    private Long stampInfoId;

    private String defaultImgUrl;

    private String usedImgUrl;
}
