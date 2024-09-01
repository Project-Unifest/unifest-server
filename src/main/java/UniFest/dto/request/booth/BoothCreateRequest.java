package UniFest.dto.request.booth;

import UniFest.domain.booth.entity.BoothCategory;
import UniFest.dto.request.menu.MenuCreateRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;
import java.util.List;


@Data
public class BoothCreateRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    @Length(min=2, max=30)
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
    @Schema(description = "부스 운영날짜", example = ": 첫째날 둘째날 셋째날 운영 시 : [1,2,3] ")
    private List<Long> openDates;
    private List<MenuCreateRequest> menus;
    @NotNull(message = "공백일 수 없습니다.")
    @Length(max=40)
    private String location;
    @NotNull(message = "공백일 수 없습니다.")
    private double latitude;
    @NotNull(message = "공백일 수 없습니다.")
    private double longitude;
    @NotNull(message = "공백일 수 없습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime openTime;
    @NotNull(message = "공백일 수 없습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime closeTime;

}
