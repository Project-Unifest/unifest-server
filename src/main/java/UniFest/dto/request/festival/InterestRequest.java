package UniFest.dto.request.festival;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InterestRequest {
    private String deviceId;
    private Long festivalId;
}
