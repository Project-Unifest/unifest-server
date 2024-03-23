package UniFest.dto.response.file;

import lombok.Getter;

@Getter
public class FileResponse {

    private String imageUrl;
    private String imageName;

    public FileResponse(String imageUrl, String imageName) {
        this.imageUrl = imageUrl;
        this.imageName = imageName;
    }

}