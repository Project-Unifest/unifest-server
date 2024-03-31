package UniFest.domain.festival.service;

import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.school.entity.School;
import UniFest.domain.school.repository.SchoolRepository;
import UniFest.domain.star.repository.EnrollRepository;
import UniFest.dto.response.festival.FestivalSearchResponse;
import UniFest.dto.request.festival.PostFestivalRequest;
import UniFest.dto.response.festival.TodayFestivalInfo;
import UniFest.dto.response.star.EnrollInfo;
import UniFest.dto.response.star.StarInfo;
import UniFest.exception.SchoolNotFoundException;
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
    private final SchoolRepository schoolRepository;
    private final EnrollRepository enrollRepository;

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
    public List<TodayFestivalInfo> getFestivalByDate(LocalDate date) {
        log.debug("[FestivalService.getFestivalByDate]");

        //축제명, 학교명, 축제id, 당일날짜
        List<TodayFestivalInfo> festivalInfo = festivalRepository.findFestivalByDate(date);
        //축제id, 연예인이름 사진
        List<EnrollInfo> starList = enrollRepository.findByDate(date);

        //TODO 성능개선
        for(TodayFestivalInfo todayFestivalInfo : festivalInfo) {
            Long festivalId = todayFestivalInfo.getFestivalId();
            for (EnrollInfo enrollInfo : starList) {
                if (enrollInfo.getFestivalId().equals(todayFestivalInfo.getFestivalId())) {
                    todayFestivalInfo.getStarList().add(new StarInfo(enrollInfo.getStarName(),
                            enrollInfo.getStarImg()));
                }
            }
        }

        return festivalInfo;
    }

    @Transactional
    public Long createFestival(PostFestivalRequest request) {
        log.debug("[FestivalService.postFestival]");

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(SchoolNotFoundException::new);

        Festival festival = new Festival(request.getName(), request.getDescription(),
                request.getThumbnail(), school, request.getBeginDate(), request.getEndDate());

        return festivalRepository.save(festival).getId();
    }
}
