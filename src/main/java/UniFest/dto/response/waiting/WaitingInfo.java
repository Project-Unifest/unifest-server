package UniFest.dto.response.waiting;

import UniFest.domain.waiting.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WaitingInfo {
    private Long boothId;
    private Long waitingId;
    private int partySize;
    private String tel;
    private String deviceId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private Integer waitingOrder;
    private String boothName;
}
