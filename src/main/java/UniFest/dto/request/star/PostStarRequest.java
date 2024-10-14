package UniFest.dto.request.star;

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
