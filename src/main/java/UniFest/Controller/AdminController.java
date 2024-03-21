package UniFest.Controller;

import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @SecurityRequirement(name = "JWT")
    @GetMapping("/admin")
    public Response admin() {

        String s = "admin";
        return Response.ofSuccess("OK", s);
    }
}
