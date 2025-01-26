package UniFest.domain.sync.service;


import UniFest.domain.Device;
import UniFest.domain.sync.repository.SyncRepository;
import UniFest.dto.request.sync.PostSyncRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SyncService {
    private final SyncRepository syncRepository;

    public void saveOrUpdateFcmToken(PostSyncRequest request) {
        Device device = Device.of(request.getDeviceId());
        syncRepository.saveSyncToken(device.getDeviceId(), request.getFcmToken());
    }

    public String getFcmToken(String deviceId) {
        Device device = Device.of(deviceId);
        return syncRepository.getSyncToken(device.getDeviceId());
    }
}
