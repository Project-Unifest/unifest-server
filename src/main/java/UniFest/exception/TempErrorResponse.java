package UniFest.exception;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class TempErrorResponse {

    private String code;
    private String message;
    private Object data;
}
