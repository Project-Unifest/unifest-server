package UniFest.domain.star.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StarInfo {

    private Long starId;
    private String name;
    private String imgUrl;
}
