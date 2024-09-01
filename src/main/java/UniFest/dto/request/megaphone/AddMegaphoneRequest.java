package UniFest.dto.request.megaphone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMegaphoneRequest {
    private Long boothId;
    private String msgBody;
}
