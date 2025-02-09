package UniFest.domain.star.controller;

import UniFest.domain.star.entity.Star;
import UniFest.domain.star.repository.StarRepository;
import UniFest.dto.request.star.PostStarRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.star.StarInfo;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public Response<List<StarInfo>> findAllStars() {
        log.debug("[StarController.findAllStars]");

        return Response.ofSuccess("OK", starRepository.findAllStars());
    }

    @GetMapping("/{starId}")
    public Response<StarInfo> findStarById(@PathVariable Long starId){
        log.debug("[StarController.findStarById] starId={}", starId);
        Optional<StarInfo> starInfo = starRepository.findById(starId).map(star -> new StarInfo(star.getId(), star.getName(), star.getImg()));
        return starInfo.map(star->Response.ofSuccess("Ok", star))
                .orElseGet(()->Response.ofNotFound("star not found", null));
    }

    @DeleteMapping("/{starId}")
    public Response<Void> deleteStar(@PathVariable Long starId){
        log.debug("[StarController.deleteStar] starId={}", starId);
        starRepository.deleteById(starId);
        return Response.ofSuccess("Star Deleted", null);
    }

    @GetMapping("/search/{name}")
    public Response<List<StarInfo>> searchStarByName(@PathVariable String name){
        log.debug("[StarController.searchStarByName] name={}", name);
        List<StarInfo> ret = starRepository.findByNameContainingIgnoreCase(name);
        return Response.ofSuccess("Search Result", ret);
    }
}
