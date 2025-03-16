package UniFest.domain.waiting.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CancelWaitingRequest {
    private Long waitingId;
    private String deviceId;
}
