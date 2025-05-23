package UniFest.domain.stamp.dto.response;

import UniFest.domain.stamp.entity.StampInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class StampInfoResponse {

    private Long stampInfoId;

    private Long festivalId;

    private String defaultImgUrl;

    private String usedImgUrl;

    @Builder
    public StampInfoResponse(StampInfo stampInfo) {
        this.stampInfoId = stampInfo.getId();
        this.festivalId = stampInfo.getFestival().getId();
        this.defaultImgUrl = stampInfo.getDefaultImgUrl();
        this.usedImgUrl = stampInfo.getUsedImgUrl();
    }
}
