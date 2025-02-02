package UniFest.domain.sync.controller;

import UniFest.domain.sync.service.SyncService;
import UniFest.dto.request.sync.PostSyncRequest;
import UniFest.dto.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/fcm-token")
public class SyncController {
    private final SyncService syncService;

    @PostMapping
    public Response<String> saveOrUpdateSyncToken(@RequestBody PostSyncRequest request) {
        syncService.saveOrUpdateFcmToken(request);
        // TODO: Exception handling
        return Response.ofSuccess("Created/Updated for device", request.getDeviceId());
    }

    @GetMapping("/{deviceId}")
    public Response<String> getSyncToken(@PathVariable String deviceId) {
        String fcmToken = syncService.getFcmToken(deviceId);
        // TODO: Exception handling
        return Response.ofSuccess("fcm token 획득", fcmToken);
    }
}
