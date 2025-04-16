package UniFest.global.infra.fcm.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class InvalidFcmTokenException extends UnifestCustomException {
    public InvalidFcmTokenException() { super(HttpStatus.BAD_REQUEST, "유효하지 않은 FCM 토큰입니다.", 1103); }
}
