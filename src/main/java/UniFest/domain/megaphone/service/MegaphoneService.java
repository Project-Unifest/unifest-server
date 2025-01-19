package UniFest.domain.megaphone.service;

import UniFest.domain.megaphone.entity.Megaphone;
import UniFest.domain.megaphone.repository.MegaphoneRepository;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.dto.request.megaphone.AddMegaphoneRequest;
import UniFest.exception.announcement.FcmFailException;
import UniFest.exception.booth.BoothNotFoundException;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MegaphoneService {
    private final BoothRepository boothRepository;
    private final MegaphoneRepository megaphoneRepository;

    public Long addMegaphone(AddMegaphoneRequest addMegaphoneRequest) {
        Long boothId = addMegaphoneRequest.getBoothId();
        Booth booth = boothRepository.findByBoothId(boothId)
                .orElseThrow(() -> new BoothNotFoundException());
        String topic = String.valueOf(booth.getFestival().getId());

        Notification notification = Notification.builder()
                .setTitle(booth.getName())
                .setBody(addMegaphoneRequest.getMsgBody())
                .build();

        Message message = Message.builder()
                .setTopic(topic)
                .setNotification(notification)
                .putData("boothId", String.valueOf(boothId))
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
