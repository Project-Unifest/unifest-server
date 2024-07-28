package UniFest.domain.announcement.service;

import UniFest.dto.request.announcement.AddAnnouncementRequest;
import UniFest.dto.request.announcement.BoothInterestRequest;
import UniFest.exception.announcement.FcmFailException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private List<String> wrapAsRegistrationTokens(BoothInterestRequest boothInterestRequest) {
        return Arrays.asList(boothInterestRequest.getFcmToken());
    }

    public void addBoothInterest(Long boothId, BoothInterestRequest boothInterestRequest) {
        List<String> registrationTokens = wrapAsRegistrationTokens(boothInterestRequest);
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .subscribeToTopic(registrationTokens, String.valueOf(boothId));
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException();
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException();
        }
    }

    public void deleteBoothInterest(Long boothId, BoothInterestRequest boothInterestRequest) {
        List<String> registrationTokens = wrapAsRegistrationTokens(boothInterestRequest);
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(registrationTokens, String.valueOf(boothId));
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException();
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException();
        }
    }

    public Long addAnnouncement(Long boothId, AddAnnouncementRequest addAnnouncementRequest) {
        Long announcementId = 0L;
        String topic = String.valueOf(boothId);

        Message message = Message.builder()
                .putData("title", addAnnouncementRequest.getTitle())
                .putData("body", addAnnouncementRequest.getBody())
                .setTopic(topic)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException();
        }

        return announcementId;
    }
}
