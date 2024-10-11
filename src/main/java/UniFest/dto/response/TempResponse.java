package UniFest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TempResponse<T> {

    private int code;
    private String message;
    private T data;

    public static <T> TempResponse<T> ofSuccess(String message, T data) {
        return new TempResponse<>(200, message, data);
    }

    public static <T> TempResponse<T> ofCreated(String message, T data) {
        return new TempResponse<>(201, message, data);
    }
    public static <T> TempResponse<T> ofFail(String message, T data) {
        return new TempResponse<>(400, message, data);
    }
    public static <T> TempResponse<T> ofUnauthorized(String message, T data) {
        return new TempResponse<>(401, message, data);
    }
    public static <T> TempResponse<T> ofForbidden(String message, T data) {
        return new TempResponse<>(403, message, data);
    }

    public static <T> TempResponse<T> ofNotFound(String message, T data) {
        return new TempResponse<>(404, message, data);
    }
}
