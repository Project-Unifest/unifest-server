package UniFest.global.infra.security.jwt.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class FilterException extends UnifestCustomException {

    public FilterException(HttpStatus httpStatus, String message, int code) {
        super(httpStatus, message, code);
    }
}
