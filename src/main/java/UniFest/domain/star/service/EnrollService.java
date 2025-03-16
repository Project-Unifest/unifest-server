package UniFest.domain.star.service;

import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.star.entity.Enroll;
import UniFest.domain.star.entity.Star;
import UniFest.domain.star.repository.EnrollRepository;
import UniFest.domain.star.repository.StarRepository;
import UniFest.domain.star.dto.request.PostEnrollRequest;
import UniFest.domain.star.dto.response.EnrollInfo;
import UniFest.domain.festival.exception.FestivalNotFoundException;
import UniFest.domain.star.exception.OutOfPeriodException;
import UniFest.domain.star.exception.StarNotFoundException;
import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollService {

    private final EnrollRepository enrollRepository;
    private final FestivalRepository festivalRepository;
    private final StarRepository starRepository;

    @Transactional
    public Long createEnroll(PostEnrollRequest request) {
        log.debug("[EnrollService.createEnroll]");

        Festival festival = festivalRepository.findById(request.getFestivalId())
                .orElseThrow(FestivalNotFoundException::new);
        Star star = starRepository.findById(request.getStarId())
                .orElseThrow(StarNotFoundException::new);
        LocalDate visitDate = request.getVisitDate();
        if (visitDate.isBefore(festival.getBeginDate()) || visitDate.isAfter(
                festival.getEndDate())) {
            throw new OutOfPeriodException();
        }
        Enroll enroll = new Enroll(
                festival, star, request.getVisitDate()
        );
        return enrollRepository.save(enroll).getId();
    }

    @Transactional(readOnly = true)
    public List<EnrollInfo> getEnrollmentsByFestival(Long festivalId) {
        return enrollRepository.findByFestivalId(festivalId)
                .stream()
                .map(enroll -> new EnrollInfo(
                        enroll.getFestival().getId(),
                        enroll.getStar().getId(),
                        enroll.getStar().getName(),
                        enroll.getStar().getImg()
                ))
                .toList();
    }

    @Transactional
    public void deleteEnrollById(Long enrollId){
        enrollRepository.deleteById(enrollId);
    }
    @Transactional(readOnly = true)
    public boolean existsById(Long enrollId) {
        return enrollRepository.existsById(enrollId);
    }
}
