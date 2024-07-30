package UniFest.domain.announcement.controller;

import UniFest.dto.request.announcement.AddAnnouncementRequest;
import UniFest.dto.request.announcement.FestivalInterestRequest;
import UniFest.domain.announcement.service.AnnouncementService;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/booths") //TODO boothController /api 접두사 삭제
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @Operation(summary = "관심 축제 등록")
    @PostMapping("/{festival-id}/interest")
    public Response postInterest(@PathVariable("festival-id") Long festivalId,
                                 @RequestBody FestivalInterestRequest festivalInterestRequest) {
        announcementService.addFestivalInterest(festivalId, festivalInterestRequest);
        return Response.ofSuccess("관심 축제 등록에 성공했습니다.", null);
    }

    @Operation(summary = "관심 축제 해제")
    @DeleteMapping("/{festival-id}/intereset")
    public Response deleteInterest(@PathVariable("festival-id") Long festivalId,
                                   @RequestBody FestivalInterestRequest festivalInterestRequest) {
        announcementService.deleteFestivalInterest(festivalId, festivalInterestRequest);
        return Response.ofSuccess("관심 축제 해제에 성공했습니다.", null);
    }

    @Operation(summary = "확성기 공지 메세지 등록")
    @PostMapping("/{booth-id}/announcement")
    public Response postAnnouncement(@PathVariable("booth-id") Long boothId,
                                     @RequestBody AddAnnouncementRequest addAnnouncementRequest) {
        Long announcementId = announcementService.addAnnouncement(boothId, addAnnouncementRequest);
        return Response.ofCreated("확성기 공지 메세지 등록에 성공했습니다", announcementId);
    }
}