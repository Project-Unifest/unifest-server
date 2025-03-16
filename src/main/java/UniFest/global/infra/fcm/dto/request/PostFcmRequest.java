package UniFest.global.infra.fcm.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFcmRequest {
    private String deviceId;
    private String fcmToken;
}
