package UniFest.dto.response.stamp;

import UniFest.domain.stamp.entity.StampInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class StampInfoResponse {

    private Long stampInfoId;

    private Long festivalId;

    private Long boothId;

    private String defaultImgUrl;

    private String usedImgUrl;

    @Builder
    public StampInfoResponse(StampInfo stampInfo) {
        this.stampInfoId = stampInfo.getId();
        this.festivalId = stampInfo.getFestival().getId();
        this.boothId = stampInfo.getBooth().getId();
        this.defaultImgUrl = stampInfo.getDefaultImgUrl();
        this.usedImgUrl = stampInfo.getUsedImgUrl();
    }
}
