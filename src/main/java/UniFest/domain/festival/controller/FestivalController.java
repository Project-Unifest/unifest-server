package UniFest.domain.festival.controller;

import UniFest.domain.festival.service.FestivalService;
import UniFest.dto.response.Response;
import UniFest.dto.response.festival.FestivalSearchResponse;
import UniFest.dto.request.festival.PostFestivalRequest;
import UniFest.dto.response.festival.TodayFestivalInfo;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/festival")
public class FestivalController {

    private final FestivalService festivalService;

    //학교명 검색
    @GetMapping("")
    public Response<List<FestivalSearchResponse>> getFestivalByName(@RequestParam("name") String schoolName) {
        log.debug("[FestivalController.getFestivalByName]");

        return Response.ofSuccess("OK", festivalService.getFestivalByName(schoolName));
    }

    //전체 검색
    @GetMapping("/all")
    public Response<List<FestivalSearchResponse>> getAllFestival() {
        log.debug("[FestivalController.getAllFestival]");

        return Response.ofSuccess("OK", festivalService.getAllFestival());
    }

    //지역별 검색
    @GetMapping("/region")
    public Response<List<FestivalSearchResponse>> getFestivalByRegion(@RequestParam("region") String region) {
        log.debug("[FestivalController.getFestivalByRegion]");

        return Response.ofSuccess("OK", festivalService.getFestivalByRegion(region));
    }

    //다가오는 축제일정
    @GetMapping("/after")
    public Response<List<FestivalSearchResponse>> getAfterFestival() {
        log.debug("[FestivalController.getAfterFestival]");

        return Response.ofSuccess("OK", festivalService.getAfterFestival());
    }

    //오늘의 축제일정
    @GetMapping("/today")
    public Response<List<TodayFestivalInfo>> getFestivalByDate(@RequestParam("date") LocalDate date) {
        log.debug("[FestivalController.getFestivalByDate]");

        return Response.ofSuccess("OK", festivalService.getFestivalByDate(date));
    }

    @PostMapping("")
    public Response<Long> postFestival(@RequestBody PostFestivalRequest request) {
        log.debug("[FestivalController.postFestival]");

        return Response.ofSuccess("Created", festivalService.createFestival(request));
    }
}
