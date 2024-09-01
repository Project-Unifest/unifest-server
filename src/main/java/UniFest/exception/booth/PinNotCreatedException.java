package UniFest.exception.booth;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class PinNotCreatedException extends UnifestCustomException {

    public PinNotCreatedException(){super(HttpStatus.BAD_REQUEST, "pin이 생성되지 않은 booth 입니다.", 4002);}
}
