package UniFest.exception.file;

import UniFest.exception.UnifestCustomException;
import org.springframework.http.HttpStatus;

public class ImageConvertingFailedException extends UnifestCustomException {

    public ImageConvertingFailedException()
    {
        super(HttpStatus.EXPECTATION_FAILED, "이미지 업로드를 위한 File 컨버팅 작업에 실패했습니다.", 3000);
    }
}
