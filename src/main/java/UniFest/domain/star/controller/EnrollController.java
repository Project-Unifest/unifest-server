package UniFest.domain.star.controller;

import UniFest.domain.star.service.EnrollService;
import UniFest.dto.request.PostEnrollRequest;
import UniFest.dto.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/enroll")
public class EnrollController {

    private final EnrollService enrollService;

    @PostMapping("")
    public Response<Long> createEnroll(@RequestBody PostEnrollRequest request) {
        log.debug("[EnrollController.createEnroll]");

        return Response.ofCreated("Created", enrollService.createEnroll(request));
    }
}
