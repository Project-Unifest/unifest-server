package UniFest.exception.stamp;

import UniFest.exception.TempUnifestCustomException;
import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class StampLimitException extends TempUnifestCustomException {

    public StampLimitException() {
        super(HttpStatus.BAD_REQUEST, "이미 최대 스탬프 갯수를 채우셨습니다", 9001);
    }
}
