package UniFest.global.infra.fcm;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class UserNoti {
    private String title;
    private String body;
    private Map<String, String> meta;

}
