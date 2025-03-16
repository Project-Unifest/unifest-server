package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class BoothNotFoundForStampException extends UnifestCustomException {
    public BoothNotFoundForStampException() {
        super(HttpStatus.BAD_REQUEST, "찾을 수 없는 Booth 입니다.", 9005);
    }
}
