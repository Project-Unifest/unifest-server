package UniFest.domain.stamp.dto.response;

import UniFest.domain.stamp.entity.StampInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class StampEnabledFestivalResponse {
    private Long festivalId;

    private String schoolName;

    private String defaultImgUrl;

    private String usedImgUrl;

    @Builder
    public StampEnabledFestivalResponse(StampInfo stampInfo, String schoolName) {
        this.festivalId = stampInfo.getFestival().getId();
        this.schoolName = schoolName;
        this.defaultImgUrl = stampInfo.getDefaultImgUrl();
        this.usedImgUrl = stampInfo.getUsedImgUrl();
    }

}
