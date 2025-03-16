package UniFest.domain.festival.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class FestivalNotFoundException extends UnifestCustomException {

    public FestivalNotFoundException()
    {
        super(HttpStatus.NOT_FOUND, "존재하지 않는 축제입니다.", 4001);
    }
}