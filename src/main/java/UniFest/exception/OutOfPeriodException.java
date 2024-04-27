package UniFest.exception;

import org.springframework.http.HttpStatus;

public class OutOfPeriodException extends UnifestCustomException {

    public OutOfPeriodException() {
        super(HttpStatus.BAD_REQUEST, "축제 기간이 아닙니다", 4002);
    }
}
