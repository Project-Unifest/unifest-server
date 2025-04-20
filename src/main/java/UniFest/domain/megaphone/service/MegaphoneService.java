package UniFest.domain.megaphone.service;

import UniFest.global.infra.fcm.UserNoti;
import UniFest.global.infra.fcm.service.FcmService;
import UniFest.domain.megaphone.entity.Megaphone;
import UniFest.domain.megaphone.repository.MegaphoneRepository;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.megaphone.dto.request.AddMegaphoneRequest;
import UniFest.global.infra.fcm.exception.FcmFailException;
import UniFest.domain.booth.exception.BoothNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MegaphoneService {
    private final BoothRepository boothRepository;
    private final MegaphoneRepository megaphoneRepository;
    private final FcmService fcmService;

    public Long addMegaphone(AddMegaphoneRequest addMegaphoneRequest) {
        Long boothId = addMegaphoneRequest.getBoothId();
        Booth booth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        String topic = String.valueOf(booth.getFestival().getId());

        Megaphone megaphone = Megaphone.builder()
                .msgBody(addMegaphoneRequest.getMsgBody())
                .booth(booth)
                .build();

        try {
            UserNoti userNoti = UserNoti.builder()
                    .title(booth.getName())
                    .body(addMegaphoneRequest.getMsgBody())
                    .meta(Map.of("boothId", String.valueOf(boothId)))
                    .build();

            fcmService.broadcast(userNoti, topic);
            megaphone.setIsSent(true);
        } catch (FcmFailException e) {
            megaphone.setIsSent(false);
            megaphone.setErrorMessage(e.getMessage());
            throw new FcmFailException(e.getMessage());
        } finally {
            megaphoneRepository.save(megaphone);
        }

        return megaphone.getId();
    }
}
