package UniFest.domain.booth.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetAllBoothResponse {

    private List<BoothResponse> booths;
    private String boothLayoutUrl;
}
