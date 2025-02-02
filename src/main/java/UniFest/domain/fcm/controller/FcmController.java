package UniFest.domain.fcm.controller;

import UniFest.domain.fcm.service.FcmService;
import UniFest.dto.request.fcm.PostFcmRequest;
import UniFest.dto.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/fcm-token")
public class FcmController {
    private final FcmService syncService;

    @PutMapping
    public Response<String> saveOrUpdateFcmToken(@RequestBody PostFcmRequest request) {
        syncService.saveOrUpdateFcmToken(request);
        // TODO: Exception handling
        return Response.ofSuccess("Created/Updated for device", request.getDeviceId());
    }

//    @GetMapping("/{deviceId}")
//    public Response<String> getFcmToken(@PathVariable String deviceId) {
//        String fcmToken = syncService.getFcmToken(deviceId);
//        // TODO: Exception handling
//        return Response.ofSuccess("fcm token 획득", fcmToken);
//    }
}
