package UniFest.domain.booth.dto.request;

import UniFest.domain.booth.entity.BoothSchedule;
import lombok.Data;

import java.util.List;

@Data
public class BoothScheduleAddRequest {
    private List<BoothSchedule> scheduleList;
}
