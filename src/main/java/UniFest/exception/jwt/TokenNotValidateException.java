package UniFest.exception.jwt;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class TokenNotValidateException extends UnifestCustomException {
    private static String message = "유효하지 않은 토큰입니다.";
    public TokenNotValidateException(String text)
    {
        super(HttpStatus.NOT_ACCEPTABLE, String.format("%s - 사유 : %s", message, text), 2000);
    }
}
