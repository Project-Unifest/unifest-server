package UniFest.domain.announcement.controller;

import UniFest.dto.request.megaphone.AddMegaphoneRequest;
import UniFest.dto.request.megaphone.SubscribeMegaphoneRequest;
import UniFest.domain.announcement.service.MegaphoneService;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/megaphone")
public class MegaphoneController {
    private final MegaphoneService announcementService;

    @Operation(summary = "관심 축제 등록")
    @PostMapping("/subscribe")
    public Response postInterest(@RequestBody SubscribeMegaphoneRequest subscribeMegaphoneRequest) {
        announcementService.addFestivalInterest(subscribeMegaphoneRequest);
        return Response.ofSuccess("관심 축제 등록에 성공했습니다.", null);
    }

    @Operation(summary = "관심 축제 해제")
    @DeleteMapping("/subscribe")
    public Response deleteInterest(@RequestBody SubscribeMegaphoneRequest subscribeMegaphoneRequest) {
        announcementService.deleteFestivalInterest(subscribeMegaphoneRequest);
        return Response.ofSuccess("관심 축제 해제에 성공했습니다.", null);
    }

    @Operation(summary = "확성기 공지 메세지 등록")
    @PostMapping
    public Response postAnnouncement(@RequestBody AddMegaphoneRequest addMegaphoneRequest) {
        Long announcementId = announcementService.addMegaphone(addMegaphoneRequest);
        return Response.ofCreated("확성기 공지 메세지 등록에 성공했습니다", announcementId);
    }
}