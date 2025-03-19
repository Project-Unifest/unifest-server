package UniFest.domain.stamp.dto.response;

import UniFest.domain.festival.entity.Festival;
import lombok.Builder;
import lombok.Data;

@Data
public class StampEnabledFestivalResponse {
    private Long festivalId;

    private String name;

    @Builder
    public StampEnabledFestivalResponse(Festival festival) {
        this.festivalId = festival.getId();
        this.name = festival.getName();
    }

}
