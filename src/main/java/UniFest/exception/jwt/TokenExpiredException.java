package UniFest.exception.jwt;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends UnifestCustomException {
    public TokenExpiredException()
    {
        super(HttpStatus.NOT_ACCEPTABLE, "만료된 액세스 토큰입니다", 2000);
    }
}
