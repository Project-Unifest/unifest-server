package UniFest.domain.school.controller;

import UniFest.domain.school.entity.School;
import UniFest.domain.school.repository.SchoolRepository;
import UniFest.domain.school.dto.request.PostSchoolRequest;
import UniFest.global.common.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/school")
public class SchoolController {

    private final SchoolRepository schoolRepository;

    @PostMapping("")
    public Response<Long> createSchool(@RequestBody PostSchoolRequest request) {
        log.debug("[SchoolController.createSchool]");

        School school = new School(
                request.getName(), request.getRegion(),
                request.getAddress(), request.getThumbnail(),
                request.getLatitude(), request.getLongitude()
        );
        return Response.ofCreated("Created", schoolRepository.save(school).getId());
    }
}
