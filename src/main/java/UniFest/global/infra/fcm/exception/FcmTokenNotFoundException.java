package UniFest.global.infra.fcm.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class FcmTokenNotFoundException extends UnifestCustomException {
    public FcmTokenNotFoundException() { super(HttpStatus.NOT_FOUND, "등록된 FCM Token이 없습니다.", 1101); }
}
