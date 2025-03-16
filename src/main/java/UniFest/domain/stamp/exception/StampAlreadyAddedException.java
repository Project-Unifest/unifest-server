package UniFest.domain.stamp.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class StampAlreadyAddedException extends UnifestCustomException {

    public StampAlreadyAddedException(){
        super(HttpStatus.BAD_REQUEST, "해당 booth에서는 이미 stamp를 찍으셨습니다", 9000);
    }
}
