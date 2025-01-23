package UniFest.dto.request.booth;

import UniFest.domain.booth.entity.BoothCategory;
import UniFest.domain.booth.entity.BoothSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;


@Data
public class BoothPatchRequest {

    private String name;
    private BoothCategory category;
    private String description;
    private String detail;
    private String thumbnail;
    private String warning;
    private String location;
    private Boolean enabled;
    private Double latitude;
    private Double longitude;

    private Boolean waitingEnabled;
}
