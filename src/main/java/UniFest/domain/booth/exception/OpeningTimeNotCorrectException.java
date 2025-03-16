package UniFest.domain.booth.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class OpeningTimeNotCorrectException extends UnifestCustomException {

    public OpeningTimeNotCorrectException() {
        super(HttpStatus.BAD_REQUEST, "폐점시간이 개점시간보다 빠릅니다.", 4001);
    }
}
