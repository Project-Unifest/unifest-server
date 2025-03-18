package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class StampInfoAlreadyAdded extends UnifestCustomException {


    public StampInfoAlreadyAdded() {
        super(HttpStatus.BAD_REQUEST, "해당 Festival에는 이미 stampInfo가 추가되어 있습니다.", 9005);
    }
}