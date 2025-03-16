package UniFest.global.infra.fileupload.service;

import UniFest.global.infra.fileupload.dto.response.FileResponse;
import UniFest.global.infra.fileupload.utils.S3ImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final S3ImageUploader s3ImageUploader;

    @Transactional
    public FileResponse uploadFile(MultipartFile file) throws IOException {
        return s3ImageUploader.uploadImage(file, getExtension(file));
    }

    private String getExtension(MultipartFile multipartFile) {
        return multipartFile.getContentType().split("/")[1];
    }

}
