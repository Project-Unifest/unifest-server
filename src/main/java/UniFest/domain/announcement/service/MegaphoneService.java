package UniFest.domain.announcement.service;

import UniFest.domain.announcement.entity.Megaphone;
import UniFest.domain.announcement.repository.MegaphoneRepository;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.dto.request.megaphone.AddMegaphoneRequest;
import UniFest.dto.request.megaphone.SubscribeMegaphoneRequest;
import UniFest.exception.announcement.FcmFailException;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.festival.FestivalNotFoundException;
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
public class MegaphoneService {
    private final FestivalRepository festivalRepository;
    private final BoothRepository boothRepository;
    private final MegaphoneRepository megaphoneRepository;

    private List<String> wrapAsRegistrationTokens(SubscribeMegaphoneRequest subscribeMegaphoneRequest) {
        return Arrays.asList(subscribeMegaphoneRequest.getFcmToken());
    }

    private void checkIsValidFestivalId(Long festivalId) {
        if (!festivalRepository.existsById(festivalId)) {
            throw new FestivalNotFoundException();
        }
    }

    public void addFestivalInterest(SubscribeMegaphoneRequest subscribeMegaphoneRequest) {
        Long festivalId = subscribeMegaphoneRequest.getFestivalId();
        checkIsValidFestivalId(festivalId);
        List<String> registrationTokens = wrapAsRegistrationTokens(subscribeMegaphoneRequest);
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .subscribeToTopic(registrationTokens, String.valueOf(festivalId));
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException(response.getErrors().get(0).getReason().toString());
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException(e.getMessage());
        }
    }

    public void deleteFestivalInterest(SubscribeMegaphoneRequest subscribeMegaphoneRequest) {
        Long festivalId = subscribeMegaphoneRequest.getFestivalId();
        checkIsValidFestivalId(festivalId);
        List<String> registrationTokens = wrapAsRegistrationTokens(subscribeMegaphoneRequest);
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .unsubscribeFromTopic(registrationTokens, String.valueOf(festivalId));
            if (response.getSuccessCount() != 1) {
                throw new FcmFailException(response.getErrors().get(0).getReason().toString());
            }
        } catch (FirebaseMessagingException e) {
            throw new FcmFailException(e.getMessage());
        }
    }

    public Long addMegaphone(AddMegaphoneRequest addMegaphoneRequest) {
        Long boothId = addMegaphoneRequest.getBoothId();
        Booth booth = boothRepository.findByBoothId(boothId)
                .orElseThrow(() -> new BoothNotFoundException());
        String topic = String.valueOf(booth.getFestival().getId());

        Message message = Message.builder()
                .putData("boothId", String.valueOf(boothId))
                .putData("boothName", booth.getName())
                .putData("msgBody", addMegaphoneRequest.getMsgBody())
                .setTopic(topic)
                .build();

        Megaphone megaphone = Megaphone.builder()
                .msgBody(addMegaphoneRequest.getMsgBody())
                .booth(booth)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            megaphone.setIsSent(true);
        } catch (FirebaseMessagingException e) {
            megaphone.setIsSent(false);
            megaphone.setErrorMessage(e.getMessage());
            throw new FcmFailException(e.getMessage());
        } finally {
            megaphoneRepository.save(megaphone);
        }

        return megaphone.getId();
    }
}
