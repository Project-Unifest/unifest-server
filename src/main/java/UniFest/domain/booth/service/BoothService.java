package UniFest.domain.booth.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.member.entity.Member;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.dto.request.booth.BoothCreateRequest;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.member.MemberNotFoundException;
import UniFest.security.userdetails.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoothService {

    private final MemberRepository memberRepository;
    private final BoothRepository boothRepository;

    @Transactional
    public Long createBooth(BoothCreateRequest boothCreateRequest, MemberDetails memberDetails) {
        //Member member = memberRepository.findByEmail(memberDetails.getEmail()).orElseThrow(MemberNotFoundException::new);
        Booth booth = Booth.builder()
                .description(boothCreateRequest.getDescription())
                .detail(boothCreateRequest.getDetail())
                .isEnabled(true)
                .latitude(boothCreateRequest.getLatitude())
                .longtitude(boothCreateRequest.getLongtitude())
                .warning(boothCreateRequest.getWarning())
                .category(boothCreateRequest.getCategory())
                .thumbnail(boothCreateRequest.getThumbnail())
                .name(boothCreateRequest.getName())
                //TODO festival 추가
                .build();
        //booth.setMember(member);
        return boothRepository.save(booth).getId();
    }

    public BoothResponse getBooth(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .filter(b -> b.isEnabled())
                .orElseThrow(BoothNotFoundException::new);
        BoothResponse response = new BoothResponse(booth);
        return response;
    }
}