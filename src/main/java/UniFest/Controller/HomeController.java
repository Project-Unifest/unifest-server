package UniFest.Controller;

import UniFest.dto.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("")
    public Response Hello() {
        String s = "hello";
        return Response.ofSuccess("OK", s);
    }
}
