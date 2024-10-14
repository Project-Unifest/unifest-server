package UniFest.exception.stamp;

import UniFest.exception.TempUnifestCustomException;
import org.springframework.http.HttpStatus;

public class BoothNotFoundForStampException extends TempUnifestCustomException {

    public BoothNotFoundForStampException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 부스 입니다.", 4000);
    }
}
