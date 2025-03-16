package UniFest.domain.stamp.dto.response;

import UniFest.domain.stamp.entity.StampRecord;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class StampRecordResponse {

    private Long stampRecordId;

    private Long stampInfoId;

    private String deviceId;

    public StampRecordResponse(StampRecord stampRecord) {
        this.stampRecordId = getStampRecordId();
        this.stampInfoId = stampRecord.getStampInfo().getId();
        this.deviceId = getDeviceId();
    }
}
