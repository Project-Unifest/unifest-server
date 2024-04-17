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
    //축제명
    private Long festivalId;
    private String festivalName;
    private LocalDate date;
    //연예인 정보
    private List<StarInfo> starList;

    public TodayFestivalInfo(Long schoolId, String schoolName, Long festivalId, String festivalName,
            LocalDate date) {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.festivalId = festivalId;
        this.festivalName = festivalName;
        this.date = date;
        this.starList = new ArrayList<>();
    }
}
