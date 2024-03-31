package UniFest.dto.request;

import UniFest.domain.festival.entity.Festival;
import UniFest.domain.star.entity.Star;
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
