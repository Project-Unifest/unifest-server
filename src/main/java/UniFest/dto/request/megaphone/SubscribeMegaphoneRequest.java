package UniFest.dto.request.megaphone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeMegaphoneRequest {
    private Long festivalId;
    private String fcmToken;
}
