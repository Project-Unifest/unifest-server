package UniFest.domain.star.controller;

import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.star.service.EnrollService;
import UniFest.dto.request.star.PostEnrollRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.star.EnrollInfo;
import UniFest.exception.festival.FestivalNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/enroll")
public class EnrollController {

    private final EnrollService enrollService;
    private final FestivalRepository festivalRepository;

    @PostMapping("")
    public Response<Long> createEnroll(@RequestBody PostEnrollRequest request) {
        log.debug("[EnrollController.createEnroll]");
        return Response.ofCreated("Created", enrollService.createEnroll(request));
    }

    @GetMapping("/festival/{festivalId}")
    public Response<List<EnrollInfo>> getEnrollmentsByFestival(@PathVariable Long festivalId) {
        log.debug("[EnrollController.getEnrollmentsByFestival] festivalId={}", festivalId);
        festivalRepository.findById(festivalId)
                .orElseThrow(() -> new FestivalNotFoundException());

        List<EnrollInfo> enrollments = enrollService.getEnrollmentsByFestival(festivalId);
        return Response.ofSuccess("OK", enrollments);
    }
    @DeleteMapping("/{enrollId}")
    public Response<Void> removeEnroll(@PathVariable Long enrollId) {
        log.debug("[EnrollController.removeEnroll] enrollId={}", enrollId);

        boolean exists = enrollService.existsById(enrollId);
        if (!exists) {
            return Response.ofNotFound("존재하지 않는 등록정보입니다", null);
        }

        enrollService.deleteEnrollById(enrollId);
        return Response.ofSuccess("Deleted", null);
    }
}
