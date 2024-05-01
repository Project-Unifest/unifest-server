package UniFest.dto.request.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostSchoolRequest {

    private String name;
    private String region;
    private String address;
    private double latitude;
    private double longitude;
    private String thumbnail;
}
