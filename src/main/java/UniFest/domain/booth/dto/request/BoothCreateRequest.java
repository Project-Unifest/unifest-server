package UniFest.domain.booth.dto.request;

import UniFest.domain.booth.entity.BoothCategory;
import UniFest.domain.menu.dto.request.MenuCreateRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;


@Data
public class BoothCreateRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    @Length(min=2, max=50)
    @Schema(description = "부스의 이름", nullable = false, maximum = "50", minimum = "2")
    private String name;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "부스의 분류", nullable = false, example = "BAR / FOOD / EVENT / NORMAL / MEDICAL / TOILET")
    private BoothCategory category;

    @Length(max=200)
    @Schema(description = "부스의 간단한 설명", nullable = true, maximum = "200")
    private String description;

    @Schema(description = "부스의 설명", nullable = true)
    private String detail;

    @Schema(description = "부스의 썸네일", nullable = true)
    private String thumbnail;

    @Length(max=100)
    @Schema(description = "부스의 경고문", nullable = true, maximum = "100")
    private String warning;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "부스의 페스티벌 아이디", nullable = false)
    private Long festivalId;

    //잠정 중지
//    @Schema(description = "부스 운영날짜", example = ": 첫째날 둘째날 셋째날 운영 시 : [1,2,3] ")
//    private List<Long> openDates;
    @Schema(description = "부스의 메뉴", nullable = true)
    private List<MenuCreateRequest> menus;

    @NotNull(message = "공백일 수 없습니다.")
    @Length(max=40)
    @Schema(description = "위치 설명", nullable = false, maximum = "40")
    private String location;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "위도", nullable = false)
    private double latitude;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "경도", nullable = false)
    private double longitude;

    @Schema(description = "일정", nullable = true)
    private List<BoothScheduleCreateRequest> scheduleList;
}
