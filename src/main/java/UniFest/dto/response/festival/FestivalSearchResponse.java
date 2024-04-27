package UniFest.dto.response.festival;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FestivalSearchResponse {

    private Long festivalId;
    private Long schoolId;
    //학교로고
    private String thumbnail;
    //학교명
    private String schoolName;
    //축제명
    private String festivalName;
    //축제기간
    private LocalDate beginDate;
    private LocalDate endDate;
    //위치
    private float latitude;
    private float longitude;
}
