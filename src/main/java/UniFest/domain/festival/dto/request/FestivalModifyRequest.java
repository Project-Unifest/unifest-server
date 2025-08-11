package UniFest.domain.festival.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FestivalModifyRequest {
    @NotNull(message = "name은 Null 일 수 없습니다.")
    @Schema(description = "축제의 이름", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "thumbnail", length = 2500)
    private String thumbnail;

    @Schema(description = "시작 날짜", nullable = false, example = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @Schema(description = "종료 날짜", nullable = false, example = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
