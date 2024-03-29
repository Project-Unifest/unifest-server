package UniFest.domain.booth.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.member.entity.Member;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.dto.request.booth.BoothCreateRequest;
import UniFest.dto.response.booth.BoothDetailResponse;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.festival.FestivalNotFoundException;
import UniFest.exception.member.MemberNotFoundException;
import UniFest.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        //Festival festival = festivalRepository.findById(boothCreateRequest.getFestivalId()).orElseThrow(FestivalNotFoundException::new);
        Booth booth = Booth.builder()
                .description(boothCreateRequest.getDescription())
                .detail(boothCreateRequest.getDetail())
                .enabled(true)
                .latitude(boothCreateRequest.getLatitude())
                .longtitude(boothCreateRequest.getLongtitude())
                .warning(boothCreateRequest.getWarning())
                .category(boothCreateRequest.getCategory())
                .thumbnail(boothCreateRequest.getThumbnail())
                .name(boothCreateRequest.getName())
                //.festival(festival)
                .build();
        booth.setMember(member);
        return boothRepository.save(booth).getId();
    }

    public BoothDetailResponse getBooth(Long boothId) {
        Booth booth = boothRepository.findByBoothId(boothId)
                .filter(b -> b.isEnabled())
                .orElseThrow(BoothNotFoundException::new);
        BoothDetailResponse response = new BoothDetailResponse(booth);
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
}
