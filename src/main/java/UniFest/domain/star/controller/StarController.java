package UniFest.domain.star.controller;

import UniFest.domain.star.entity.Star;
import UniFest.domain.star.repository.StarRepository;
import UniFest.dto.request.star.PostStarRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.star.StarInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/star")
public class StarController {

    private final StarRepository starRepository;

    @PostMapping("")
    public Response<Long> createStar(@RequestBody PostStarRequest request) {
        log.debug("[StarController.createStar]");
        Star star = new Star(
                request.getName(),
                request.getImgUrl()
        );

        return Response.ofCreated("Created", starRepository.save(star).getId());
    }
}
