package UniFest.domain.festival.service;

import UniFest.domain.Device;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.entity.Interest;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.festival.repository.InterestRepository;
import UniFest.exception.festival.FestivalNotFoundException;
import UniFest.exception.festival.InterestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InterestService {
    private final FestivalRepository festivalRepository;
    private final InterestRepository interestRepository;

    public void addFestivalInterest(String deviceId, Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(FestivalNotFoundException::new);
        if (interestRepository.existsByDeviceIdAndFestivalId(deviceId, festivalId)) {
            return;
        }
        interestRepository.save(new Interest(festival, Device.of(deviceId)));
    }

    public void deleteFestivalInterest(String deviceId, Long festivalId) {
        Interest interest = interestRepository.findByDeviceIdAndFestivalId(deviceId, festivalId)
                .orElseThrow(InterestNotFoundException::new);
        interestRepository.delete(interest);
    }
}
