package UniFest.domain.booth.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.entity.BoothSchedule;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.booth.repository.BoothScheduleRepository;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.member.entity.Member;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.dto.request.booth.BoothCreateRequest;
import UniFest.dto.request.booth.BoothPatchRequest;
import UniFest.dto.response.booth.BoothDetailResponse;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.exception.auth.NotAuthorizedException;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.festival.FestivalNotFoundException;
import UniFest.exception.member.MemberNotFoundException;
import UniFest.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothService {

    private final MemberRepository memberRepository;
    private final BoothRepository boothRepository;
    private final FestivalRepository festivalRepository;
    private final BoothScheduleRepository boothScheduleRepository;

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
        LocalDate beginDate = festival.getBeginDate();
        LocalDate endDate = festival.getEndDate();
        for(Long turn : boothCreateRequest.getOpenDates()){
            LocalDate opendate = beginDate.plusDays(turn-1);
            if(opendate.isAfter(endDate) || opendate.isBefore(beginDate)) throw new RuntimeException("부스 운영날짜는 축제날짜에 포함되어야 합니다.");
            BoothSchedule schedule = BoothSchedule.builder()
                    .openDate(opendate)
                    .build();
            schedule.setBooth(booth);
            boothScheduleRepository.save(schedule);
        }
        return boothRepository.save(booth).getId();
    }

    //value::key의 형태로 redis key 생성
    @Cacheable(value = "BoothInfo", key = "#boothId",cacheManager = "redisCacheManager")
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
                .stream().filter(b -> b.isEnabled())
                .map(BoothResponse::new).toList();
        return responses;
    }

    @Transactional
    public List<BoothResponse> getTrendingBooths(Long festivalId){
        Festival festival = festivalRepository.findById(festivalId).orElseThrow();
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
        Optional.ofNullable(boothPatchRequest.isEnabled())
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
        return findBooth.getId();
    }
    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public void deleteBooth(MemberDetails memberDetails, Long boothId) {
        Booth findBooth = verifyAuth(memberDetails.getMemberId(), boothId);
        boothRepository.delete(findBooth);
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
    public void deleteBoothSchedule() {
        LocalDate now = LocalDate.now();
        boothScheduleRepository.deleteBoothSchedule(now);
    }

    public Booth verifyAuth(Long memberId, Long boothId){
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        if(findBooth.getMember().getId() != memberId) throw new NotAuthorizedException();
        return findBooth;
    }
}
