package UniFest.global.common.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private int code;
    private String message;
    private Object data;
}
