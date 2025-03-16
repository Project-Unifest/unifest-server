package UniFest.domain.member.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends UnifestCustomException {
    public MemberNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다", 1001);
    }
}
