package UniFest.dto.response.file;

import lombok.Getter;

@Getter
public class FileResponse {

    private String imgUrl;
    private String imageName;

    public FileResponse(String imgUrl, String imageName) {
        this.imgUrl = imgUrl;
        this.imageName = imageName;
    }

}
