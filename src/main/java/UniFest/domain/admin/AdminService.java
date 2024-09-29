package UniFest.domain.admin;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.member.entity.Member;
import UniFest.domain.member.repository.MemberRepository;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.member.MemberNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final BoothRepository boothRepository;
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
}
