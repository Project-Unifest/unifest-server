package UniFest.exception.file;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class UploadSizeExceedException extends UnifestCustomException {

    public UploadSizeExceedException() {
        super(HttpStatus.PAYLOAD_TOO_LARGE, "업로드 가능한 이미지 용량을 초과하였습니다.", 3001);
    }
}
