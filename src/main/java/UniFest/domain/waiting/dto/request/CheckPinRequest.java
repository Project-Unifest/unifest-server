package UniFest.domain.waiting.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckPinRequest {
    private Long boothId;
    private String pinNumber;
}
