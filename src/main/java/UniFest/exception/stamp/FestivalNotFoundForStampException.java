package UniFest.exception.stamp;

import UniFest.exception.TempUnifestCustomException;
import org.springframework.http.HttpStatus;

public class FestivalNotFoundForStampException extends TempUnifestCustomException {

    public FestivalNotFoundForStampException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 부스 입니다.", 9003);
    }
}
