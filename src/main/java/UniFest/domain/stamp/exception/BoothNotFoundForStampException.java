package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class BoothNotFoundForStampException extends UnifestCustomException {

    public BoothNotFoundForStampException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 부스 입니다.", 9003);
    }
}
