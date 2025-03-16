package UniFest.global.infra.fileupload.dto.response;

import lombok.Getter;

@Getter
public class FileResponse {

    private String imgUrl;
    private String imgName;

    public FileResponse(String imgUrl, String imgName) {
        this.imgUrl = imgUrl;
        this.imgName = imgName;
    }

}
