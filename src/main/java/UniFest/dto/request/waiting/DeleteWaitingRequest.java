package UniFest.dto.request.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteWaitingRequest {
    private Long waitingId;
    private String deviceId;
}
