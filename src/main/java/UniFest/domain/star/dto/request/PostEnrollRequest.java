package UniFest.domain.star.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostEnrollRequest {

    @NotNull(message = "festivalId는 null이면 안됩니다.")
    @Schema(description = "페스티벌 Id", nullable = false)
    private Long festivalId;

    @NotNull(message = "starId는 null이면 안됩니다.")
    @Schema(description = "공연 출연자 Id", nullable = false)
    private Long starId;

    @NotNull(message = "visitDate는 null이면 안됩니다.")
    @Schema(description = "공연 출연자 방문일", nullable = false, example = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate visitDate;
}
