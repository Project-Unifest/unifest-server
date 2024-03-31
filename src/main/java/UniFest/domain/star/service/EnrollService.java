package UniFest.domain.star.service;

import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.star.entity.Enroll;
import UniFest.domain.star.entity.Star;
import UniFest.domain.star.repository.EnrollRepository;
import UniFest.domain.star.repository.StarRepository;
import UniFest.dto.request.PostEnrollRequest;
import UniFest.exception.FestivalNotFoundException;
import UniFest.exception.StarNotFoundException;
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

    public Long createEnroll(PostEnrollRequest request) {
        log.debug("[EnrollService.createEnroll]");

        Festival festival = festivalRepository.findById(request.getFestivalId())
                .orElseThrow(FestivalNotFoundException::new);
        Star star = starRepository.findById(request.getStarId())
                .orElseThrow(StarNotFoundException::new);
        Enroll enroll = new Enroll(
                festival, star, request.getVisitDate()
        );
        return enrollRepository.save(enroll).getId();
    }
}
