package UniFest.domain.stamp.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.stamp.entity.StampInfo;
import UniFest.domain.stamp.entity.StampRecord;
import UniFest.domain.stamp.repository.StampRecordRepository;
import UniFest.domain.stamp.repository.StampInfoRepository;
import UniFest.dto.request.stamp.StampInfoCreateRequest;
import UniFest.dto.request.stamp.StampInfoUpdateRequest;
import UniFest.dto.response.stamp.StampInfoResponse;
import UniFest.dto.response.stamp.StampRecordResponse;
import UniFest.exception.booth.BoothNotFoundException;
import UniFest.exception.festival.FestivalNotFoundException;
import UniFest.exception.stamp.BoothNotFoundForStampException;
import UniFest.exception.stamp.StampAlreadyAddedException;
import UniFest.exception.stamp.StampLimitException;
import UniFest.exception.stamp.StampNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StampService {

    private final StampInfoRepository stampInfoRepository;
    private final StampRecordRepository stampRecordRepository;
    private final BoothRepository boothRepository;
    private final FestivalRepository festivalRepository;

    private int stampLimit = 100;

    @Transactional
    public Long addStamp(Long boothId, String deviceId){
        Booth booth = boothRepository.findByBoothId(boothId).orElseThrow(BoothNotFoundForStampException::new);

        if(!booth.isStampEnabled()){ //스탬프 못찍는 부스일 시
            throw new StampNotEnabledException();
        }
        StampInfo stampInfo = booth.getStampInfo();

        StampRecord stampRecord = stampRecordRepository.findByDeviceIdAndStampInfo(deviceId, stampInfo).orElse(null);

        if (stampRecord != null) {
            throw new StampAlreadyAddedException();
        }
        if (isMaxStamp(deviceId)){
            throw new StampLimitException();
        }
        stampRecord = new StampRecord(stampInfo, deviceId);
        stampRecordRepository.save(stampRecord);

        List<StampRecord> tokenStampListInfo = stampRecordRepository.findByDeviceId(deviceId);
        return stampRecord.getId();
    }

    public List<StampRecordResponse> getStamp(String deviceId){
        List<StampRecordResponse> stampRecords = stampRecordRepository.findByDeviceId(deviceId)
                .stream()
                .map(StampRecordResponse::new)
                .collect(Collectors.toList());

        for (StampRecordResponse stampRecord : stampRecords) {
            stampRecord.getStampRecordId();
            stampRecord.getDeviceId();
        }

        return stampRecords;
    }

    public List<StampInfoResponse> getStampInfo(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        List<StampInfoResponse> stampInfoResponseList = stampInfoRepository.findByFestival(festival)
                .stream()
                .map(StampInfoResponse::new)
                .collect(Collectors.toList());

        return stampInfoResponseList;
    }

    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public Long createStampInfo(StampInfoCreateRequest stampInfoCreateRequest){
        Booth booth = boothRepository.findByBoothId(stampInfoCreateRequest.getBoothId()).orElseThrow(BoothNotFoundException::new);
        Festival festival = festivalRepository.findById(stampInfoCreateRequest.getFestivalId()).orElseThrow(FestivalNotFoundException::new);

        String defaultImgUrl = stampInfoCreateRequest.getDefaultImgUrl();
        String usedImgUrl = stampInfoCreateRequest.getUsedImgUrl();

        StampInfo stampInfo = new StampInfo(festival, booth, defaultImgUrl, usedImgUrl);
        stampInfoRepository.save(stampInfo);
        booth.setStampInfo(stampInfo);
        booth.updateStampEnabled(true);

        return stampInfo.getId();
    }

    @Transactional
    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public Long deleteStampInfo(Long boothId){
        Booth booth = boothRepository.findByBoothId(boothId).orElseThrow(BoothNotFoundException::new);
        StampInfo stampInfo = booth.getStampInfo();

        if(stampInfo == null){
            throw new StampNotEnabledException();
        }

        booth.setStampInfo(null);
        stampInfoRepository.delete(stampInfo);
        booth.updateStampEnabled(false);

        return booth.getId();
    }

    private boolean isMaxStamp(String deviceId){
        List<StampRecord> stampRecords = stampRecordRepository.findByDeviceId(deviceId);

        if(stampRecords.size() >= stampLimit){
            return true;
        }
        return false;
    }
}
