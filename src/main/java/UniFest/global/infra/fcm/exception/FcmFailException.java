package UniFest.global.infra.fcm.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class FcmFailException extends UnifestCustomException {
    public FcmFailException() { super(HttpStatus.INTERNAL_SERVER_ERROR, "FCM 처리에 실패했습니다.", 1002); }
    public FcmFailException(String errorMsg) { super(HttpStatus.INTERNAL_SERVER_ERROR, "FCM 처리에 실패했습니다." + errorMsg, 1002); }
}
