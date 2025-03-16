package UniFest.domain.festival.service;

import UniFest.domain.Device;
import UniFest.global.infra.fcm.service.FcmService;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.entity.Interest;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.festival.repository.InterestRepository;
import UniFest.domain.festival.exception.FestivalNotFoundException;
import UniFest.domain.festival.exception.InterestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InterestService {
    private final FestivalRepository festivalRepository;
    private final InterestRepository interestRepository;
    private final FcmService fcmService;

    public void addFestivalInterest(String deviceId, Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(FestivalNotFoundException::new);
        if (interestRepository.existsByDeviceIdAndFestivalId(deviceId, festivalId)) {
            return;
        }
        fcmService.subscribe(deviceId, String.valueOf(festivalId));
        interestRepository.save(new Interest(festival, Device.of(deviceId)));
    }

    public void deleteFestivalInterest(String deviceId, Long festivalId) {
        Interest interest = interestRepository.findByDeviceIdAndFestivalId(deviceId, festivalId)
                .orElseThrow(InterestNotFoundException::new);
        fcmService.unsubscribe(deviceId, String.valueOf(festivalId));
        interestRepository.delete(interest);
    }

    public List<Long> getInterestedFestivalIds(String deviceId) {
        return interestRepository.findByDeviceId(deviceId)
                .stream()
                .map(interest -> interest.getFestival().getId())
                .toList();
    }
}
