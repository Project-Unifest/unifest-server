package UniFest.dto.response.stamp;

import lombok.Data;

import java.util.List;

@Data
public class StampRecordListResponse {
    List<StampRecordResponse> stampRecordResponseList;

    public StampRecordListResponse(List<StampRecordResponse> stampRecordResponseList) {
        this.stampRecordResponseList = stampRecordResponseList;
    }
}
