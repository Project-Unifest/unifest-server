package UniFest.domain.fcm.service;


import UniFest.domain.Device;
import UniFest.domain.fcm.repository.FcmRepository;
import UniFest.dto.request.sync.PostSyncRequest;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FcmService {
    private final FcmRepository fcmRepository;

    public void saveOrUpdateFcmToken(PostSyncRequest request) {
        Device device = Device.of(request.getDeviceId());
        fcmRepository.saveSyncToken(device.getDeviceId(), request.getFcmToken());
    }

    public String getFcmToken(String deviceId) {
        Device device = Device.of(deviceId);
        return fcmRepository.getSyncToken(device.getDeviceId());
    }
}
