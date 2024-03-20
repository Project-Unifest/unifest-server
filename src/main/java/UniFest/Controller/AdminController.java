package UniFest.Controller;

import UniFest.dto.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public Response admin() {

        String s = "admin";
        return Response.ofSuccess("OK", s);
    }
}
