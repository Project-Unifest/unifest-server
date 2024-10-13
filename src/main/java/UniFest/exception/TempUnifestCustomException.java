package UniFest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TempUnifestCustomException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    private final Object data;

    public TempUnifestCustomException(HttpStatus httpStatus, String message, int code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = Integer.toString(code);
        this.data = null;
    }

}
