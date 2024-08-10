package UniFest.dto.request.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostWaitingRequest {
    private Long boothId;
    private String tel;
    private String deviceId;
    private int partySize;
    private String pinNumber;
}
