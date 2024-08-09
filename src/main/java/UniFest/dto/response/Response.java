package UniFest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {

    private String code;
    private String message;
    private T data;

    public static <T> Response<T> ofSuccess(String message, T data) {
        return new Response<>("200", message, data);
    }

    public static <T> Response<T> ofCreated(String message, T data) {
        return new Response<>("201", message, data);
    }
    public static <T> Response<T> ofFail(String message, T data) {
        return new Response<>("400", message, data);
    }
    public static <T> Response<T> ofUnauthorized(String message, T data) {
        return new Response<>("401", message, data);
    }
    public static <T> Response<T> ofForbidden(String message, T data) {
        return new Response<>("403", message, data);
    }

    public static <T> Response<T> ofNotFound(String message, T data) {
        return new Response<>("404", message, data);
    }
}
