package UniFest.domain.stamp.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.stamp.entity.Stamp;
import UniFest.domain.stamp.repository.StampRepository;
import UniFest.exception.stamp.StampAlreadyAddedException;
import UniFest.exception.stamp.StampLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StampService {

    private final StampRepository stampRepository;
    private final BoothRepository boothRepository;

    private int stampLimit = 5;

    @Transactional
    public int addStamp(Long boothId, String token){
        Booth booth = boothRepository.getReferenceById(boothId);

        Stamp stampResult = stampRepository.findByTokenAndBoothId(token, boothId).orElse(null);

        if (stampResult != null) {
            throw new StampAlreadyAddedException();
        }
        if (isMaxStamp(token)){
            throw new StampLimitException();
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

    private boolean isMaxStamp(String token){
        List<Stamp> stampByTokenList = stampRepository.findByToken(token);
        if(stampByTokenList.size() >= stampLimit){
            return true;
        }
        return false;
    }
}
