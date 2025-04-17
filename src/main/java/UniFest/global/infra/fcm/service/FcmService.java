package UniFest.global.infra.fcm.service;

import UniFest.domain.Device;
import UniFest.global.infra.fcm.FcmUtils;
import UniFest.global.infra.fcm.UserNoti;
import UniFest.global.infra.fcm.exception.FcmFailException;
import UniFest.global.infra.fcm.exception.FcmTokenNotFoundException;
import UniFest.global.infra.fcm.repository.FcmRepository;
import UniFest.global.infra.fcm.dto.request.PostFcmRequest;
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
        FcmUtils.verifyFcmToken(request.getFcmToken());
        fcmRepository.saveFcmToken(device.getDeviceId(), request.getFcmToken());
    }

    public String getFcmToken(String deviceId) {
        Device device = Device.of(deviceId);
        String token = fcmRepository.getFcmToken(device.getDeviceId());
        if (token == null) {
            throw new FcmTokenNotFoundException();
        }
        return token;
    }

    public void subscribe(String deviceId, String topic) {
        FcmUtils.subscribe(getFcmToken(deviceId), topic);
    }

    public void unsubscribe(String deviceId, String topic) {
        FcmUtils.unsubscribe(getFcmToken(deviceId), topic);
    }

    public void broadcast(UserNoti userNoti, String topic) throws FcmFailException {
        FcmUtils.sendWithTopic(userNoti, topic);
    }

    public void send(UserNoti userNoti, String deviceId) {
        FcmUtils.sendWithToken(userNoti, getFcmToken(deviceId));
    }
}
