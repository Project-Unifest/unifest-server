package UniFest.domain.festival.service;

import UniFest.domain.festival.dto.request.FestivalModifyRequest;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.exception.FestivalNotFoundException;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.school.entity.School;
import UniFest.domain.school.repository.SchoolRepository;
import UniFest.domain.star.repository.EnrollRepository;
import UniFest.domain.festival.dto.response.FestivalSearchResponse;
import UniFest.domain.festival.dto.request.PostFestivalRequest;
import UniFest.domain.festival.dto.response.TodayFestivalInfo;
import UniFest.domain.star.dto.response.EnrollInfo;
import UniFest.domain.star.dto.response.StarInfo;
import UniFest.domain.school.exception.SchoolNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "FestivalInfo", cacheManager = "redisCacheManager")
    public List<FestivalSearchResponse> getAllFestival() {
        log.debug("[FestivalService.getAllFestival]");

        return festivalRepository.findAllFestival();
    }

    //지역별 검색
    @Cacheable(value = "FestivalInfo", key = "#region",cacheManager = "redisCacheManager")
    public List<FestivalSearchResponse> getFestivalByRegion(String region) {
        log.debug("[FestivalService.getFestivalByRegion]");

        return festivalRepository.findFestivalByRegion(region);
    }

    //다가오는 축제일정
    public List<FestivalSearchResponse> getAfterFestival() {
        log.debug("[FestivalService.getAfterFestival]");

        return festivalRepository.findAfterFestival(PageRequest.of(0,5));
    }

    public List<TodayFestivalInfo> getFestivalByDateRevision(LocalDate date){
        // TODO: map, groupingBy 써서 in-memory 줄임 -> 애초에 쿼리는 2번 발생에서 변하지 않음 (개선 필요)
        List<TodayFestivalInfo> festivalInfo = festivalRepository.findFestivalByDate(date);
        List<EnrollInfo> starList = enrollRepository.findByDate(date);
        Map<Long, List<EnrollInfo>> enrollMap = starList.stream()
                .collect(Collectors.groupingBy(EnrollInfo::getFestivalId));
        for (TodayFestivalInfo todayFestivalInfo : festivalInfo) {
            Long festivalId = todayFestivalInfo.getFestivalId();
            List<EnrollInfo> enrollInfos = enrollMap.getOrDefault(festivalId, List.of());
            for (EnrollInfo enrollInfo : enrollInfos) {
                todayFestivalInfo.getStarList().add(
                        new StarInfo(enrollInfo.getStarId(),
                                enrollInfo.getStarName(),
                                enrollInfo.getImgUrl())
                );
            }
        }

        return festivalInfo;
    }

    @Transactional
    @CacheEvict(value = "FestivalInfo", allEntries = true)
    public Long createFestival(PostFestivalRequest request) {
        log.debug("[FestivalService.postFestival]");

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(SchoolNotFoundException::new);

        Festival festival = new Festival(request.getName(), request.getDescription(),
                request.getThumbnail(), school, request.getBeginDate(), request.getEndDate());

        return festivalRepository.save(festival).getId();
    }

    @Transactional
    @CacheEvict(value = "FestivalInfo", key = "#festivalId")
    public Long modifyFestival(Long festivalId,FestivalModifyRequest request) {
        Festival findFestival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);

        Optional.ofNullable(request.getName())
                .ifPresent(name -> findFestival.setName(name));
        Optional.ofNullable(request.getDescription())
                .ifPresent(description -> findFestival.setDescription(description));
        Optional.ofNullable(request.getThumbnail())
                .ifPresent(thumbnail -> findFestival.setThumbnail(thumbnail));
        Optional.ofNullable(request.getBeginDate())
                .ifPresent(beginDate -> findFestival.setBeginDate(beginDate));
        Optional.ofNullable(request.getEndDate())
                .ifPresent(endDate -> findFestival.setEndDate(endDate));

        return festivalRepository.save(findFestival).getId();
    }

    @Transactional
    @CacheEvict(value = "FestivalInfo", key = "#festivalId")
    public void deleteFestival(Long festivalId) {
        festivalRepository.deleteById(festivalId);
    }
}
