package UniFest.dto.request.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CheckPinRequest {
    private Long boothId;
    private String pinNumber;
}
