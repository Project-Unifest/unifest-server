package UniFest.exception.booth;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class BoothNotFoundException extends UnifestCustomException {


    public BoothNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않은 부스입니다", 4000);
    }
}
