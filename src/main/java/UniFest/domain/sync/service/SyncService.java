package UniFest.domain.sync.service;


import UniFest.domain.Device;
import UniFest.domain.sync.repository.SyncRepository;
import UniFest.dto.request.sync.PostSyncRequest;

public class SyncService {
    private final SyncRepository syncRepository;

    public SyncService(SyncRepository syncRepository) {
        this.syncRepository = syncRepository;
    }

    public void saveOrUpdateFcmToken(PostSyncRequest request) {
        Device device = Device.of(request.getDeviceId());
        syncRepository.saveSyncToken(device.getDeviceId(), request.getFcmToken());
    }

    public String getFcmToken(String deviceId) {
        Device device = Device.of(deviceId);
        return syncRepository.getSyncToken(device.getDeviceId());
    }
}
