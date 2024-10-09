package UniFest.exception.stamp;

import UniFest.exception.TempUnifestCustomException;
import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class StampNotEnabledException extends TempUnifestCustomException {

    public StampNotEnabledException() {
        super(HttpStatus.BAD_REQUEST, "스탬프를 찍을 수 없는 Booth 입니다", 9002);
    }
}
