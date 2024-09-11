package UniFest.dto.request.waiting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckPinRequest {
    private Long boothId;
    private String pinNumber;
}
