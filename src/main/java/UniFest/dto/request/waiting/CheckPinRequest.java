package UniFest.dto.request.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckPinRequest {
    private Long boothId;
    private String pinNumber;
}
