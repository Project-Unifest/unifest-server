package UniFest.domain.stamp.dto.response;

import UniFest.domain.stamp.entity.StampRecord;
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
