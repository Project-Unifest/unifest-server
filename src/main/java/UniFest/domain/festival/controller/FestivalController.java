package UniFest.domain.festival.controller;

import UniFest.domain.festival.service.FestivalService;
import UniFest.dto.response.Response;
import UniFest.dto.response.festival.FestivalSearchResponse;
import UniFest.dto.request.festival.PostFestivalRequest;
import UniFest.dto.response.festival.TodayFestivalInfo;
import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/festival")
public class FestivalController {

    private final FestivalService festivalService;


    @Operation(summary = "학교명 검색")
    @GetMapping("")
    public Response<List<FestivalSearchResponse>> getFestivalByName(@RequestParam("name") String schoolName) {
        log.debug("[FestivalController.getFestivalByName]");

        return Response.ofSuccess("OK", festivalService.getFestivalByName(schoolName));
    }


    @Operation(summary = "전체 검색")
    @GetMapping("/all")
    public Response<List<FestivalSearchResponse>> getAllFestival() {
        log.debug("[FestivalController.getAllFestival]");

        return Response.ofSuccess("OK", festivalService.getAllFestival());
    }

    @Operation(summary = "지역별 검색")
    @GetMapping("/region")
    public Response<List<FestivalSearchResponse>> getFestivalByRegion(@RequestParam("region") String region) {
        log.debug("[FestivalController.getFestivalByRegion]");

        return Response.ofSuccess("OK", festivalService.getFestivalByRegion(region));
    }


    @Operation(summary = "다가오는 축제일정")
    @GetMapping("/after")
    public Response<List<FestivalSearchResponse>> getAfterFestival() {
        log.debug("[FestivalController.getAfterFestival]");

        return Response.ofSuccess("OK", festivalService.getAfterFestival());
    }


    @Operation(summary = "오늘의 축제일정")
    @GetMapping("/today")
    public Response<List<TodayFestivalInfo>> getFestivalByDate(@RequestParam("date") LocalDate date) {
        log.debug("[FestivalController.getFestivalByDate]");

        return Response.ofSuccess("OK", festivalService.getFestivalByDate(date));
    }

    @Operation(summary = "축제 등록")
    @PostMapping("")
    public Response<Long> postFestival(@RequestBody PostFestivalRequest request) {
        log.debug("[FestivalController.postFestival]");

        return Response.ofCreated("Created", festivalService.createFestival(request));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "축제 삭제")
    @DeleteMapping("/{festival-id}")
    public Response deleteBooth(@PathVariable("festival-id") Long festivalId) {
        festivalService.deleteFestival(festivalId);
        return Response.ofSuccess("OK",null);
    }
}