package UniFest.domain.booth.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class BoothNotFoundException extends UnifestCustomException {


    public BoothNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 부스입니다", 3001);
    }
}
