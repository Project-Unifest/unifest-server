package UniFest.domain.stamp.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StampRecordListResponse {
    List<StampRecordResponse> stampRecordResponseList;

    public StampRecordListResponse(List<StampRecordResponse> stampRecordResponseList) {
        this.stampRecordResponseList = stampRecordResponseList;
    }
}
