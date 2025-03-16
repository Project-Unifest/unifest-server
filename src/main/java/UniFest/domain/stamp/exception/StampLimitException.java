package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class StampLimitException extends UnifestCustomException {

    public StampLimitException() {
        super(HttpStatus.BAD_REQUEST, "이미 최대 스탬프 갯수를 채우셨습니다", 9001);
    }
}
