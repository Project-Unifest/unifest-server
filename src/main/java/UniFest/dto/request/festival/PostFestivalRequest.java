package UniFest.dto.request.festival;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostFestivalRequest {

    private Long schoolId;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String name;
    private String description;
    private String thumbnail;
}
