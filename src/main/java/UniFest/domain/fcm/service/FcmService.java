package UniFest.domain.fcm.service;

import UniFest.domain.Device;
import UniFest.domain.fcm.repository.FcmRepository;
import UniFest.dto.request.fcm.PostFcmRequest;
import UniFest.exception.announcement.FcmFailException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public void send(Message message) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().send(message);
        //TODO issue랑 send 구분해서 send면 deviceId, issue면 topic 받기
        //TODO 외부에서는 FCM이 제공하는 Message, Notification 모르게 숨기기
    }

    public void subscribe(String deviceId, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .subscribeToTopic(Collections.singletonList(getFcmToken(deviceId)), topic);
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException(response.getErrors().get(0).getReason());
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException(e.getMessage());
        }
    }

    public void unsubscribe(String deviceId, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(Collections.singletonList(getFcmToken(deviceId)), topic);
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException(response.getErrors().get(0).getReason());
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException(e.getMessage());
        }
    }
}
