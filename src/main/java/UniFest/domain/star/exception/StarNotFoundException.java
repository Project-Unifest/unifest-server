package UniFest.domain.star.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class StarNotFoundException extends UnifestCustomException {

    public StarNotFoundException() {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 연예인입니다.", 5001);
    }
}
