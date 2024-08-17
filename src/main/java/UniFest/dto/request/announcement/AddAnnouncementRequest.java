package UniFest.dto.request.announcement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAnnouncementRequest {
    private String msgBody;
}
