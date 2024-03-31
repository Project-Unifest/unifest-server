package UniFest.dto.request.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSchoolRequest {

    private String name;
    private String region;
    private String address;
    private float latitude;
    private float longitude;
    private String thumbnail;
}
