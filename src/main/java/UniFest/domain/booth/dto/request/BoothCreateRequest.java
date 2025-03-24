package UniFest.domain.booth.dto.request;

import UniFest.domain.booth.entity.BoothCategory;
import UniFest.domain.menu.dto.request.MenuCreateRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;


@Data
public class BoothCreateRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    @Length(min=2, max=50)
    private String name;
    @NotNull(message = "공백일 수 없습니다.")
    private BoothCategory category;
    @Length(max=200)
    private String description;
    private String detail;
    private String thumbnail;
    @Length(max=100)
    private String warning;
    private Long festivalId;
//    @Schema(description = "부스 운영날짜", example = ": 첫째날 둘째날 셋째날 운영 시 : [1,2,3] ")
//    private List<Long> openDates;
    private List<MenuCreateRequest> menus;
    @NotNull(message = "공백일 수 없습니다.")
    @Length(max=40)
    private String location;
    @NotNull(message = "공백일 수 없습니다.")
    private double latitude;
    @NotNull(message = "공백일 수 없습니다.")
    private double longitude;

    private List<BoothScheduleCreateRequest> scheduleList;
}
