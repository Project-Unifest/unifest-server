package UniFest.domain.booth.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BoothScheduleCreateRequest {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Schema(description = "날짜", nullable = false, example = "YYYY-MM-DD")
    LocalDate date;

    @NotNull
    @Schema(description = "개점 시간",example = "HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    LocalTime openTime;

    @NotNull
    @Schema(description = "폐점 시간",example = "HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    LocalTime closeTime;
}
