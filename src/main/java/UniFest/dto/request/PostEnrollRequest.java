package UniFest.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostEnrollRequest {

    private Long festivalId;
    private Long starId;
    private LocalDate visitDate;
}
