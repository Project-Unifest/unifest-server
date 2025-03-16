package UniFest.domain.star.controller;

import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.star.service.EnrollService;
import UniFest.domain.star.dto.request.PostEnrollRequest;
import UniFest.global.common.response.Response;
import UniFest.domain.star.dto.response.EnrollInfo;
import UniFest.domain.festival.exception.FestivalNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary="festival:star enroll 정보 생성")
    public Response<Long> createEnroll(@RequestBody PostEnrollRequest request) {
        log.debug("[EnrollController.createEnroll]");
        return Response.ofCreated("Created", enrollService.createEnroll(request));
    }

    @GetMapping("/festival/{festivalId}")
    @Operation(summary = "festival 기준 festival:star 등록정보 조회")
    public Response<List<EnrollInfo>> getEnrollmentsByFestival(@PathVariable Long festivalId) {
        log.debug("[EnrollController.getEnrollmentsByFestival] festivalId={}", festivalId);
        festivalRepository.findById(festivalId)
                .orElseThrow(() -> new FestivalNotFoundException());

        List<EnrollInfo> enrollments = enrollService.getEnrollmentsByFestival(festivalId);
        return Response.ofSuccess("OK", enrollments);
    }
    @DeleteMapping("/{enrollId}")
    @Operation(summary = "축제에 등록된 enroll 제거")
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
