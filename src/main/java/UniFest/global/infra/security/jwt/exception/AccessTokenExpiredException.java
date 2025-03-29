package UniFest.global.infra.security.jwt.exception;

import org.springframework.http.HttpStatus;

public class AccessTokenExpiredException extends FilterException {
    public AccessTokenExpiredException()
    {
        super(HttpStatus.UNAUTHORIZED, "만료된 액세스 토큰입니다", 2000);
    }
}
