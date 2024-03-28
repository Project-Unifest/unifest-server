package UniFest.domain.festival.service;

import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.dto.response.festival.FestivalSearchResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FestivalService {

    private final FestivalRepository festivalRepository;

    //학교명 검색
    public List<FestivalSearchResponse> getFestivalByName(String schoolName) {
        log.debug("[FestivalService.getFestivalByName]");

        schoolName = "%" + schoolName + "%";
        return festivalRepository.findFestivalBySchool(schoolName);
    }

    //전체 검색
    public List<FestivalSearchResponse> getAllFestival() {
        log.debug("[FestivalService.getAllFestival]");

        return festivalRepository.findAllFestival();
    }

    //지역별 검색
    public List<FestivalSearchResponse> getFestivalByRegion(String region) {
        log.debug("[FestivalService.getFestivalByRegion]");

        return festivalRepository.findFestivalByRegion(region);
    }

    //다가오는 축제일정
    public List<FestivalSearchResponse> getAfterFestival() {
        log.debug("[FestivalService.getAfterFestival]");

        return festivalRepository.findAfterFestival(PageRequest.of(0,5));
    }

    //오늘의 축제일정
    public List<FestivalSearchResponse> getFestivalByDate(LocalDate date) {
        log.debug("[FestivalService.getFestivalByDate]");

        return festivalRepository.findFestivalByDate(date);
    }

}
