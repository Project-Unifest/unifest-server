package UniFest.exception.jwt;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class AccessTokenExpiredException extends UnifestCustomException {
    public AccessTokenExpiredException()
    {
        super(HttpStatus.NOT_ACCEPTABLE, "만료된 액세스 토큰입니다", 2000);
    }
}
