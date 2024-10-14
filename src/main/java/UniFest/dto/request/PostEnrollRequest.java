package UniFest.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.formatter.qual.Format;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostEnrollRequest {

    private Long festivalId;
    private Long starId;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate visitDate;
}
