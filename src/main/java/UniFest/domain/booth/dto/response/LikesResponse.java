package UniFest.domain.booth.dto.response;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.entity.Likes;
import lombok.Data;

@Data
public class LikesResponse {
    private Long id;

    private Booth booth;

    private String token;
    public LikesResponse(Likes like){
        this.id = like.getId();
        this.booth = like.getBooth();
        this.token = like.getToken();
    }
}
