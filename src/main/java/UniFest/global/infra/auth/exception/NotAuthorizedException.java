package UniFest.global.infra.auth.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends UnifestCustomException {
    public NotAuthorizedException() {
        super(HttpStatus.BAD_REQUEST, "해당 서비스에 접근할 수 없습니다.", 1001);
    }
}
