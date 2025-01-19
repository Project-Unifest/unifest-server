package UniFest.domain.megaphone.controller;

import UniFest.dto.request.megaphone.AddMegaphoneRequest;
import UniFest.domain.megaphone.service.MegaphoneService;
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

    @Operation(summary = "확성기 공지 메세지 등록")
    @PostMapping
    public Response postAnnouncement(@RequestBody AddMegaphoneRequest addMegaphoneRequest) {
        Long announcementId = announcementService.addMegaphone(addMegaphoneRequest);
        return Response.ofCreated("확성기 공지 메세지 등록에 성공했습니다", announcementId);
    }
}