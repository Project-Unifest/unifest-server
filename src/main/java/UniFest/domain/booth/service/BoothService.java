package UniFest.domain.booth.service;

import UniFest.domain.booth.dto.request.BoothCreateRequest;
import UniFest.domain.booth.dto.request.BoothPatchRequest;
import UniFest.domain.booth.dto.request.BoothScheduleCreateRequest;
import UniFest.domain.booth.dto.request.BoothSchedulePatchRequest;
import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.entity.BoothSchedule;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.booth.repository.BoothScheduleRepository;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.member.entity.Member;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.domain.menu.entity.Menu;
import UniFest.domain.menu.repository.MenuRepository;
import UniFest.domain.menu.dto.request.MenuCreateRequest;
import UniFest.domain.stamp.dto.request.StampEnabledRequest;
import UniFest.domain.booth.dto.response.BoothDetailResponse;
import UniFest.domain.booth.dto.response.BoothResponse;
import UniFest.global.infra.auth.exception.NotAuthorizedException;
import UniFest.domain.booth.exception.BoothNotFoundException;
import UniFest.domain.booth.exception.PinNotCreatedException;
import UniFest.domain.festival.exception.FestivalNotFoundException;
import UniFest.domain.member.exception.MemberNotFoundException;
import UniFest.global.infra.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothService {

    private final MemberRepository memberRepository;
    private final BoothRepository boothRepository;
    private final FestivalRepository festivalRepository;
    private final BoothScheduleRepository boothScheduleRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public Long createBooth(BoothCreateRequest boothCreateRequest, MemberDetails memberDetails) {
        Member member = memberRepository.findByEmail(memberDetails.getEmail()).orElseThrow(MemberNotFoundException::new);
        Festival festival = festivalRepository.findById(boothCreateRequest.getFestivalId()).orElseThrow(FestivalNotFoundException::new);
        Booth booth = Booth.builder()
                .description(boothCreateRequest.getDescription())
                .detail(boothCreateRequest.getDetail())
                .enabled(true)
                .location(boothCreateRequest.getLocation())
                .latitude(boothCreateRequest.getLatitude())
                .longitude(boothCreateRequest.getLongitude())
                .warning(boothCreateRequest.getWarning())
                .category(boothCreateRequest.getCategory())
                .thumbnail(boothCreateRequest.getThumbnail())
                .name(boothCreateRequest.getName())
                .festival(festival)
                .build();
        booth.setMember(member);
        //영업 시간
        if(boothCreateRequest.getScheduleList() != null) {
            for (BoothScheduleCreateRequest time : boothCreateRequest.getScheduleList()) {
                BoothSchedule newSchedule = new BoothSchedule(time.getDate(), time.getOpenTime(), time.getCloseTime());
                newSchedule.setBooth(booth);
                booth.addSchedule(newSchedule);
            }
        }

//        LocalTime openTime = boothCreateRequest.getOpenTime();
//        LocalTime closeTime = boothCreateRequest.getCloseTime();
//        if(openTime != null || closeTime != null) {
//            setBoothOpeningHour(booth, openTime, closeTime);
//        }
        //오픈날짜
        LocalDate beginDate = festival.getBeginDate();
        LocalDate endDate = festival.getEndDate();
//        for(Long turn : boothCreateRequest.getOpenDates()){
//            LocalDate opendate = beginDate.plusDays(turn-1);
//            if(opendate.isAfter(endDate) || opendate.isBefore(beginDate)) throw new RuntimeException("부스 운영날짜는 축제날짜에 포함되어야 합니다.");
//            BoothSchedule schedule = BoothSchedule.builder()
//                    .openDate(opendate)
//                    .build();
//            schedule.setBooth(booth);
//            boothScheduleRepository.save(schedule);
//        }
        //핀 생성
        booth.createPin();
        //부스 메뉴
        if(boothCreateRequest.getMenus() != null) {
            for (MenuCreateRequest menuCreateRequest : boothCreateRequest.getMenus()) {
                Menu menu = Menu.builder()
                        .name(menuCreateRequest.getName())
                        .price(menuCreateRequest.getPrice())
                        .imgUrl(menuCreateRequest.getImgUrl())
                        .menuStatus(menuCreateRequest.getMenuStatus())
                        .build();
                menu.setBooth(booth);
                menuRepository.save(menu).getId();
            }
        }

        return boothRepository.save(booth).getId();
    }

    //value::key의 형태로 redis key 생성
    @Cacheable(value = "BoothInfo", key = "#boothId", cacheManager = "redisCacheManager")
    public BoothDetailResponse getBooth(Long boothId) {
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .filter(b -> b.isEnabled())
                .orElseThrow(BoothNotFoundException::new);
        BoothDetailResponse response = new BoothDetailResponse(findBooth);
        return response;
    }

    public List<BoothResponse> getBooths(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        List<BoothResponse> responses = boothRepository.findAllByFestivalAndEnabled(festival,true)
                .stream().map(BoothResponse::new).toList();
        return responses;
    }

    @Transactional
    public List<BoothResponse> getTrendingBooths(Long festivalId){
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        List<BoothResponse> boothResponseList = new ArrayList<>();
        List<Booth> boothList = boothRepository.findTop5ByFestivalOrderByLikesListSizeDesc(festival);
        for(Booth booth : boothList){
            boothResponseList.add(new BoothResponse(booth));
        }

        return boothResponseList;
    }


    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public Long updateBooth(BoothPatchRequest boothPatchRequest, MemberDetails memberDetails, Long boothId) {
        Booth findBooth = verifyAuth(memberDetails.getMemberId(), boothId);
        Optional.ofNullable(boothPatchRequest.getEnabled())
                .ifPresent(enabled -> findBooth.updateEnabled(enabled));
        Optional.ofNullable(boothPatchRequest.getName())
                .ifPresent(name -> findBooth.updateName(name));
        Optional.ofNullable(boothPatchRequest.getCategory())
                .ifPresent(category -> findBooth.updateCategory(category));
        Optional.ofNullable(boothPatchRequest.getDescription())
                .ifPresent(description -> findBooth.updateDescription(description));
        Optional.ofNullable(boothPatchRequest.getDetail())
                .ifPresent(detail -> findBooth.updateDetail(detail));
        Optional.ofNullable(boothPatchRequest.getThumbnail())
                .ifPresent(thumb -> findBooth.updateThumbnail(thumb));
        Optional.ofNullable(boothPatchRequest.getWarning())
                .ifPresent(warning -> findBooth.updateWarning(warning));
        Optional.ofNullable(boothPatchRequest.getLocation())
                .ifPresent(loc -> findBooth.updateLocation(loc));
        Optional.ofNullable(boothPatchRequest.getLatitude())
                .ifPresent(lat -> findBooth.updateLatitude(lat));
        Optional.ofNullable(boothPatchRequest.getLongitude())
                .ifPresent(lng -> findBooth.updateLongitude(lng));
        Optional.ofNullable(boothPatchRequest.getWaitingEnabled())
                .ifPresent(waiting -> findBooth.updateWaitingEnabled(waiting));
        return findBooth.getId();
    }

    @Transactional
    public void deleteBoothWithAuth(MemberDetails memberDetails, Long boothId) {
        verifyAuth(memberDetails.getMemberId(), boothId);
        deleteBooth(boothId);
    }

    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public void deleteBooth(Long boothId) {
        boothRepository.deleteById(boothId);
    }

    @Transactional
    public void updateBoothEnabled() {
        LocalDate now = LocalDate.now();
        boothRepository.updateBoothEnabled(now, true);
    }

    @Transactional
    public void updateBoothDisabled() {
        LocalDate now = LocalDate.now();
        boothRepository.updateBoothDisabled(now, false);
    }

    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public void deleteBoothSchedule(Long boothId, Long scheduleId) {
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        List<BoothSchedule> scheduleList = findBooth.getScheduleList();
        scheduleList.stream()
                .filter(boothSchedule -> boothSchedule.getId().equals(scheduleId))
                .collect(Collectors.toList())
                .forEach(boothSchedule -> {
                    scheduleList.remove(boothSchedule);
                });
    }

    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public Long updateBoothSchedule(Long boothId, BoothSchedulePatchRequest boothSchedulePatchRequest) {
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        List<BoothSchedule> scheduleList = findBooth.getScheduleList();
        scheduleList.clear();
        boothSchedulePatchRequest.getScheduleList()
                .forEach(boothScheduleForm -> {
                    BoothSchedule boothSchedule = new BoothSchedule(boothScheduleForm.getDate(), boothScheduleForm.getOpenTime(), boothScheduleForm.getCloseTime());
                    boothSchedule.setBooth(findBooth);
                    scheduleList.add(boothSchedule);
                });
        return boothId;
    }

    private Booth verifyAuth(Long memberId, Long boothId){
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
//        log.info("findBooth Member = " + findBooth.getMember().getId().getClass().getName() + " memberId = " + findBooth.getMember().getId());
//        log.info("memberId Member = " + memberId.getClass().getName() + " memberId = " + memberId);
//        log.info("findBooth.getMember().getId() != memberId = " + Boolean.valueOf(findBooth.getMember().getId() != memberId).toString());
        if(!findBooth.getMember().getId().equals(memberId)) throw new NotAuthorizedException();
        return findBooth;
    }

    public String getPin(Long boothId){
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        //pin이 발급되지 않았을 경우 에러
        String boothPin = findBooth.getPin();
        if(boothPin == null){
            throw new PinNotCreatedException();
        }

        return boothPin;
    }

    @Transactional
    public String createPin(Long boothId){
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);

        String newPin = findBooth.createPin();

        return newPin;
    }

    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public boolean updateBoothWaitingEnabled(Long boothId){
        Booth findBooth = boothRepository.findByBoothId(boothId).orElseThrow(BoothNotFoundException::new);

        findBooth.updateWaitingEnabled(!findBooth.isWaitingEnabled());  //toggle 형식으로 작동
        return findBooth.isWaitingEnabled();
    }

    public void setBoothOpeningHour(Booth booth, LocalTime openTime, LocalTime closeTime) {
//        booth.setOpeningHour(openTime, closeTime);
    }

    public List<BoothResponse> getStampEnabledBooths(Long festivalId){
        Festival findFestival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        List<BoothResponse> stampEnabledBooths = boothRepository.findBoothsByFestivalAndStampEnabled(findFestival, true)
                .stream().filter(b -> b.isEnabled())
                .map(BoothResponse::new).toList();
        return stampEnabledBooths;
    }

    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public boolean updateStampEnabled(Long boothId, StampEnabledRequest stampEnabledRequest){
        Booth findBooth = boothRepository.findByBoothId(boothId).orElseThrow(BoothNotFoundException::new);

        findBooth.updateStampEnabled(stampEnabledRequest.getStampEnabled()  );
        return findBooth.isStampEnabled();
    }

}