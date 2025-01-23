package UniFest.dto.request.booth;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BoothSchedulePatchRequest {

    List<BoothScheduleCreateRequest> scheduleList;
}
