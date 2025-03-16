package UniFest.global.common.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
}
