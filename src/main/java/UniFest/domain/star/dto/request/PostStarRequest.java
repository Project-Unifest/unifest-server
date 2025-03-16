package UniFest.domain.star.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostStarRequest {
    private String name;
    private String imgUrl;
}
