package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class FestivalNotFoundForStampException extends UnifestCustomException {

    public FestivalNotFoundForStampException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 Festival 입니다.", 9004);
    }
}
