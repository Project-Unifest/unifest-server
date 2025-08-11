package UniFest.domain.booth.dto.response;

import UniFest.domain.booth.entity.BoothSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoothScheduleResponse {
    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate date;

    @Schema(example = "HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    LocalTime openTime;

    @Schema(example = "HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    LocalTime closeTime;

    public BoothScheduleResponse(BoothSchedule boothSchedule) {
        this.id = boothSchedule.getId();
        this.date = boothSchedule.getOpenDate();
        this.openTime = boothSchedule.getOpenTime();
        this.closeTime = boothSchedule.getCloseTime();
    }
}
