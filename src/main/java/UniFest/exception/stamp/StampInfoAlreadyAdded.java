package UniFest.exception.stamp;

import UniFest.exception.TempUnifestCustomException;
import org.springframework.http.HttpStatus;

public class StampInfoAlreadyAdded extends TempUnifestCustomException {

    public StampInfoAlreadyAdded() {
        super(HttpStatus.BAD_REQUEST, "이미 StampInfo가 존재합니다.", 9004);
    }
}
