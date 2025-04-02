package UniFest.domain.school.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class SchoolNotFoundException extends UnifestCustomException {

    public SchoolNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않은 학교입니다", 7001);
    }
}
