package UniFest.domain.stamp.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.stamp.entity.Stamp;
import UniFest.domain.stamp.repository.StampRepository;
import UniFest.exception.stamp.StampAlreadyAddedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final BoothRepository boothRepository;

    public int addStamp(Long boothId, String token){
        Booth booth = boothRepository.getReferenceById(boothId);

        Stamp stampResult = stampRepository.findByTokenAndBoothId(token, boothId).orElse(null);

        if (stampResult != null) {
            throw new StampAlreadyAddedException();
        }
        stampResult = new Stamp(booth, token);
        stampRepository.save(stampResult);

        List<Stamp> tokenStampList = stampRepository.findByToken(token);
        return tokenStampList.size();
    }

    public int getStamp(String token){
        List<Stamp> byTokenList = stampRepository.findByToken(token);
        return byTokenList.size();
    }
}
