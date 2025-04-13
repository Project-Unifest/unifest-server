package UniFest.domain.festival.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class InterestAlreadyExistsException extends UnifestCustomException {

    public InterestAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST, "이미 이 디바이스로 관심 등록한 축제입니다.", 4002);
    }
}
