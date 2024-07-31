package UniFest.exception.announcement;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class FcmFailException extends UnifestCustomException {
    public FcmFailException() { super(HttpStatus.INTERNAL_SERVER_ERROR, "FCM 처리에 실패했습니다.", 8000); }
}
