package UniFest.domain.festival.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostFestivalRequest {

    @NotNull(message = "schoolId는 Null 일 수 없습니다.")
    @Schema(name = "학교 Id", nullable = false)
    private Long schoolId;

    @NotNull(message = "beginDate는 Null 일 수 없습니다.")
    @Schema(name = "시작 날짜", nullable = false, example = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @NotNull(message = "endDate는 Null 일 수 없습니다.")
    @Schema(name = "종료 날짜", nullable = false, example = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "name은 Null 일 수 없습니다.")
    @Schema(name = "축제의 이름", nullable = false)
    private String name;

    @NotNull(message = "description은 Null 일 수 없습니다.")
    @Schema(name = "축제 설명", nullable = false)
    private String description;

    @NotNull(message = "thumbnail은 Null 일 수 없습니다.")
    @Schema(name = "축제 썸네일", nullable = false)
    private String thumbnail;
}
