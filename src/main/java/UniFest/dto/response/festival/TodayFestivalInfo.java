package UniFest.dto.response.festival;

import UniFest.dto.response.star.StarInfo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class TodayFestivalInfo {
    private Long schoolId;
    //학교명
    private String schoolName;
    private String thumbnail;
    //축제명
    private Long festivalId;
    private String festivalName;
    private LocalDate beginDate;
    private LocalDate endDate;
    //연예인 정보
    private List<StarInfo> starList;

    public TodayFestivalInfo(Long schoolId, String schoolName, String thumbnail, Long festivalId, String festivalName,
            LocalDate beginDate, LocalDate endDate) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.thumbnail = thumbnail;
        this.festivalId = festivalId;
        this.festivalName = festivalName;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.starList = new ArrayList<>();
    }
}
