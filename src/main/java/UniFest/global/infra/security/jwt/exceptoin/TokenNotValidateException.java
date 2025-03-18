package UniFest.global.infra.security.jwt.exceptoin;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class TokenNotValidateException extends UnifestCustomException {

    public TokenNotValidateException()
    {
        super(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", 2001);
    }
}
