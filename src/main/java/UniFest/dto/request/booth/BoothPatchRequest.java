package UniFest.dto.request.booth;

import UniFest.domain.booth.entity.BoothCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalTime;


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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime openTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime closeTime;
}
