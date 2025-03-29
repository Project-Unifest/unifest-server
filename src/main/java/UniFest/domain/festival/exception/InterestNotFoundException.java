package UniFest.domain.festival.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class InterestNotFoundException extends UnifestCustomException {

    public InterestNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 디바이스의 해당 축제에 대한 관심이 존재하지 않습니다.", 4003);
    }
}
