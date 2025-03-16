package UniFest.domain.member.exception;

import UniFest.global.common.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class MemberEmailExistException extends UnifestCustomException {
    public MemberEmailExistException() {
        super(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.", 1000);
    }
}
