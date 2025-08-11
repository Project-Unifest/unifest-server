package UniFest.global.infra.security.jwt.exception;

import org.springframework.http.HttpStatus;

public class TokenNotValidateException extends FilterException {

    public TokenNotValidateException()
    {
        super(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", 2002);
    }
}
