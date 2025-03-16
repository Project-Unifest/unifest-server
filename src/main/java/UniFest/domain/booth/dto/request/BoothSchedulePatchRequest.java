package UniFest.domain.booth.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BoothSchedulePatchRequest {

    List<BoothScheduleCreateRequest> scheduleList;
}
