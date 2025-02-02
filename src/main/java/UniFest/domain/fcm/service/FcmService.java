package UniFest.domain.fcm.service;


import UniFest.domain.Device;
import UniFest.domain.fcm.repository.FcmRepository;
import UniFest.dto.request.fcm.PostFcmRequest;
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

    public void saveOrUpdateFcmToken(PostFcmRequest request) {
        Device device = Device.of(request.getDeviceId());
        fcmRepository.saveFcmToken(device.getDeviceId(), request.getFcmToken());
    }

    public String getFcmToken(String deviceId) {
        Device device = Device.of(deviceId);
        return fcmRepository.getFcmToken(device.getDeviceId());
    }
}
