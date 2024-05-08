package UniFest.exception.menu;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class MenuNotFoundException extends UnifestCustomException {


    public MenuNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 메뉴입니다.", 6000);
    }
}
