package UniFest.exception.jwt;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class TokenNotValidateException extends UnifestCustomException {

    public TokenNotValidateException()
    {
        super(HttpStatus.NOT_ACCEPTABLE, "유효하지 않은 토큰입니다.", 2001);
    }
}
