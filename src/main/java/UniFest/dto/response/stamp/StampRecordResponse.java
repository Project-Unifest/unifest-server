package UniFest.dto.response.stamp;

import UniFest.domain.stamp.entity.StampInfo;
import UniFest.domain.stamp.entity.StampRecord;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StampRecordResponse {

    private Long stampRecordId;

    private Long boothId;

    private String deviceId;

    public StampRecordResponse(StampRecord stampRecord) {
        this.stampRecordId = stampRecord.getId();
        this.boothId = stampRecord.getBooth().getId();
        this.deviceId = stampRecord.getDeviceId();
    }
}
