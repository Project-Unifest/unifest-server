package UniFest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class UnifestCustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
    private final Object data;

    public UnifestCustomException(HttpStatus httpStatus, String message, int code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
        this.data = null;
    }

}
