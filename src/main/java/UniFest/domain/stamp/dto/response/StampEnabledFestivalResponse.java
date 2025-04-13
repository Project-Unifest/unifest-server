package UniFest.domain.stamp.dto.response;

import UniFest.domain.festival.entity.Festival;
import UniFest.domain.stamp.entity.StampInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class StampEnabledFestivalResponse {
    private Long festivalId;

    private String name;

    private String defaultImgUrl;

    private String usedImgUrl;

    @Builder
    public StampEnabledFestivalResponse(StampInfo stampInfo) {
        this.festivalId = stampInfo.getFestival().getId();
        this.name = stampInfo.getFestival().getName();
        this.defaultImgUrl = stampInfo.getDefaultImgUrl();
        this.usedImgUrl = stampInfo.getUsedImgUrl();
    }

}
