package UniFest.domain.star.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EnrollInfo {

    private Long festivalId;
    private Long starId;
    private String starName;
    private String imgUrl;
}
