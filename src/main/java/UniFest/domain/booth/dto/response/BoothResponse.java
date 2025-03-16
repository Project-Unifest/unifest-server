package UniFest.domain.booth.dto.response;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.entity.BoothCategory;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;


@Data
public class BoothResponse {
    private Long id;

    private String name;

    private BoothCategory category;

    private String description;

    private String thumbnail;

    private String location;

    private double latitude;

    private double longitude;

    private boolean enabled;

    private boolean waitingEnabled;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime openTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime closeTime;

    private boolean stampEnabled;

    public BoothResponse(Booth booth){
        this.id = booth.getId();
        this.name = booth.getName();
        this.category = booth.getCategory();
        this.description = booth.getDescription();
        this.thumbnail = booth.getThumbnail();
        this.location = booth.getLocation();
        this.latitude = booth.getLatitude();
        this.longitude = booth.getLongitude();
        this.enabled = booth.isEnabled();
        this.waitingEnabled = booth.isWaitingEnabled();
        this.stampEnabled = booth.isStampEnabled();
    }
}
