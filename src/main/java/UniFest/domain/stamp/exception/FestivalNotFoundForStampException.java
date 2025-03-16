package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class FestivalNotFoundForStampException extends UnifestCustomException {
    public FestivalNotFoundForStampException() {
        super(HttpStatus.BAD_REQUEST, "찾을 수 없는 Festival 입니다.", 9003);
    }
}
