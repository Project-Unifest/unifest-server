package UniFest.global.infra.security.jwt.exceptoin;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class RefreshTokenExpiredException extends UnifestCustomException {
    public RefreshTokenExpiredException()
    {
        super(HttpStatus.NOT_ACCEPTABLE, "만료된 리프레시 토큰입니다", 2003);
    }
}
