package UniFest.dto.response.waiting;

import UniFest.domain.waiting.entity.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime updatedAt;

    private ReservationStatus status;
    private Integer waitingOrder;
    private String boothName;
}
