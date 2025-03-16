package UniFest.dto.response.stamp;

import UniFest.domain.festival.entity.Festival;
import UniFest.domain.stamp.entity.StampInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class StampEnabledFestival {
    private Long festivalId;

    private String name;

    @Builder
    public StampEnabledFestival(Festival festival) {
        this.festivalId = festival.getId();
        this.name = festival.getName();
    }

}
