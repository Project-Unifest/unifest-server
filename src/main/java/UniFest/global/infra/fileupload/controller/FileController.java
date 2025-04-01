package UniFest.global.infra.fileupload.controller;

import UniFest.global.common.response.Response;
import UniFest.global.infra.fileupload.dto.response.FileResponse;
import UniFest.global.infra.fileupload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response<FileResponse> uploadFile(@RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        FileResponse fileResponse = fileService.uploadFile(file);
        return Response.ofSuccess("OK", fileResponse);
    }
}
