package UniFest.exception.jwt;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class TokenSignatureException extends UnifestCustomException {
    public TokenSignatureException()
    {
        super(HttpStatus.NOT_ACCEPTABLE, "JWT 시그니처 정보가 잘못되었습니다.", 2002);
    }
}
