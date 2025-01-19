package UniFest.dto.request.sync;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSyncRequest {
    private String deviceId;
    private String fcmToken;
}
