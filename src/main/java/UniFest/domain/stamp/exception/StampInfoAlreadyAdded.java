package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class StampInfoAlreadyAdded extends UnifestCustomException {

    public StampInfoAlreadyAdded() {
        super(HttpStatus.BAD_REQUEST, "이미 StampInfo가 존재합니다.", 9004);
    }
}
