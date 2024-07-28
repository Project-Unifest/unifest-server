package UniFest.domain.announcement.controller;

import UniFest.dto.request.announcement.AddAnnouncementRequest;
import UniFest.dto.request.announcement.BoothInterestRequest;
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

    @Operation(summary = "관심 부스 등록")
    @PostMapping("/{booth-id}/interest")
    public Response postInterest(@PathVariable("booth-id") Long boothId,
                                 @RequestBody BoothInterestRequest boothInterestRequest) {
        announcementService.addBoothInterest(boothId, boothInterestRequest);
        return Response.ofSuccess("관심 부스 등록에 성공했습니다.", null);
    }

    @Operation(summary = "관심 부스 해제")
    @DeleteMapping("/{booth-id}/intereset")
    public Response deleteInterest(@PathVariable("booth-id") Long boothId,
                                   @RequestBody BoothInterestRequest boothInterestRequest) {
        announcementService.deleteBoothInterest(boothId, boothInterestRequest);
        return Response.ofSuccess("관심 부스 해제에 성공했습니다.", null);
    }

    @Operation(summary = "확성기 공지 메세지 등록")
    @PostMapping("/{booth-id}/announcement")
    public Response postAnnouncement(@PathVariable("booth-id") Long boothId,
                                     @RequestBody AddAnnouncementRequest addAnnouncementRequest) {
        Long announcementId = announcementService.addAnnouncement(boothId, addAnnouncementRequest);
        return Response.ofCreated("확성기 공지 메세지 등록에 성공했습니다", announcementId);
    }
}