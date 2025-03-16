package UniFest.domain.festival.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalSearchResponse {

    private Long festivalId;
    private Long schoolId;
    //학교로고
    private String thumbnail;
    //학교명
    private String schoolName;
    //지역명
    private String region;
    //축제명
    private String festivalName;
    //축제기간
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    //위치
    private double latitude;
    private double longitude;
}
