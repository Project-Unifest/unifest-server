package UniFest.domain.booth.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return boothRepository.save(booth).getId();
    }

    //value::key의 형태로 redis key 생성
    @Cacheable(key = "#boothId",value = "BoothInfo" ,cacheManager = "redisCacheManager")
    public BoothDetailResponse getBooth(Long boothId) {
        log.info("[특정 부스 조회]");
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .filter(b -> b.isEnabled())
                .orElseThrow(BoothNotFoundException::new);
        BoothDetailResponse response = new BoothDetailResponse(findBooth);
        return response;
    }

    public List<BoothResponse> getBooths(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        List<BoothResponse> responses = boothRepository.findAllByFestivalAndEnabled(festival,true).stream().map(BoothResponse::new).toList();
        return responses;
    }

    @Transactional
    public List<BoothDetailResponse> getTrendingBooths(Long festivalId){
        Festival festival = festivalRepository.findById(festivalId).orElseThrow();
        List<BoothDetailResponse> boothDetailResponseList = new ArrayList<>();
        List<Booth> boothList = boothRepository.findTop5ByFestivalOrderByLikesListSizeDesc(festival);

        for(Booth booth : boothList){
            boothDetailResponseList.add(new BoothDetailResponse(booth));
        }

        return boothDetailResponseList;
    }
    @Transactional
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
    public void deleteBooth(MemberDetails memberDetails, Long boothId) {
        Booth findBooth = verifyAuth(memberDetails.getMemberId(), boothId);
        boothRepository.delete(findBooth);
    }

    public Booth verifyAuth(Long memberId, Long boothId){
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        if(findBooth.getMember().getId() != memberId) throw new NotAuthorizedException();
        return findBooth;
    }
}
