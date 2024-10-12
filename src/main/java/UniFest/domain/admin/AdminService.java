package UniFest.domain.admin;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.member.entity.Member;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.dto.response.booth.BoothDetailResponse;
import UniFest.dto.response.booth.BoothResponse;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.festival.FestivalNotFoundException;
import UniFest.exception.member.MemberNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final BoothRepository boothRepository;
    private final FestivalRepository festivalRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void changeBoothOwner(Long boothId, Long newOwnerId) {
        Booth booth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        Member newOwner = memberRepository.findById(newOwnerId)
                .orElseThrow(MemberNotFoundException::new);
        booth.setMember(newOwner);
        boothRepository.save(booth);
    }

    public BoothDetailResponse getBoothEvenDisabled(Long boothId) {
        Booth findBooth = boothRepository.findByBoothId(boothId)
                .orElseThrow(BoothNotFoundException::new);
        return new BoothDetailResponse(findBooth);
    }

    public List<BoothResponse> getBoothsIncludingDisabled(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        return boothRepository.findAllByFestival(festival)
                .stream().map(BoothResponse::new).toList();
    }


}
