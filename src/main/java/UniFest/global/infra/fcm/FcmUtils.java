package UniFest.global.infra.fcm;

import UniFest.global.infra.fcm.exception.FcmFailException;
import com.google.firebase.messaging.*;

import java.util.Collections;
import java.util.Map;

public final class FcmUtils {
    private FcmUtils() {}

    public static void verifyFcmToken(String fcmToken) {
        //TODO verify fcm token
//        try {
//            FirebaseAuth.getInstance().verifyIdToken(fcmToken);
//        } catch (FirebaseAuthException e) {
//            throw new InvalidFcmTokenException();
//        }
    }

    public static void subscribe(String fcmToken, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .subscribeToTopic(Collections.singletonList(fcmToken), topic);
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException(response.getErrors().get(0).getReason());
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException(e.getMessage());
        }
    }

    public static void unsubscribe(String fcmToken, String topic) {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(Collections.singletonList(fcmToken), topic);
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException(response.getErrors().get(0).getReason());
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException(e.getMessage());
        }
    }

    //TODO token 객체로 감싸기
    public static void sendWithToken(UserNoti userNoti, String token) throws FcmFailException {
        send(Message.builder().setToken(token), userNoti);

    public static void sendWithTopic(UserNoti userNoti, String topic) throws FcmFailException {
        send(Message.builder().setTopic(topic), userNoti);
    }

    private static void send(Message.Builder messageBuilder, UserNoti userNoti) throws FcmFailException {
        Notification notification = Notification.builder()
                .setTitle(userNoti.getTitle())
                .setBody(userNoti.getBody())
                .build();

        messageBuilder.setNotification(notification);

        Map<String, String> meta = userNoti.getMeta();
        if (meta != null && !meta.isEmpty()) {
            messageBuilder.putAllData(meta);
        }

        Message message = messageBuilder.build();
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException(e.getMessage());
        }
    }
}
