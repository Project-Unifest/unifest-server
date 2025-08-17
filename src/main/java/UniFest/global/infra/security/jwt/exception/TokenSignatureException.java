package UniFest.global.infra.security.jwt.exception;

import org.springframework.http.HttpStatus;

public class TokenSignatureException extends FilterException {
    public TokenSignatureException()
    {
        super(HttpStatus.UNAUTHORIZED, "JWT 시그니처 정보가 잘못되었습니다.", 2002);
    }
}
