package UniFest.domain.festival.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class InterestNotFoundException extends UnifestCustomException {

    public InterestNotFoundException() {
        super(HttpStatus.NOT_FOUND, "관심 등록한 적이 없습니다.", 4001);
    }
}
