package UniFest.dto.request.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostWaitingRequest {
    private Long boothId;
    private String tel;
    private String deviceId;
    private int partySize;
    private String pinNumber;
}
