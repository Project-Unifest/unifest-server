package UniFest.dto.request.booth;

import UniFest.domain.booth.entity.BoothCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
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
    private List<Long> openDates;
    @NotNull(message = "공백일 수 없습니다.")
    @Length(max=40)
    private String location;
    @NotNull(message = "공백일 수 없습니다.")
    private double latitude;
    @NotNull(message = "공백일 수 없습니다.")
    private double longitude;

}
