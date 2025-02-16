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

    private Long stampInfoId;

    private String deviceId;

    public StampRecordResponse(StampRecord stampRecord) {
        this.stampRecordId = getStampRecordId();
        this.stampInfoId = stampRecord.getStampInfo().getId();
        this.deviceId = getDeviceId();
    }
}
