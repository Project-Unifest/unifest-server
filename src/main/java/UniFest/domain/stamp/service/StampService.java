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
import UniFest.dto.response.booth.BoothResponse;
import UniFest.dto.response.stamp.StampInfoResponse;
import UniFest.dto.response.stamp.StampRecordResponse;
import UniFest.exception.festival.FestivalNotFoundException;
import UniFest.exception.stamp.*;
import lombok.RequiredArgsConstructor;
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
        Booth booth = boothRepository.findByBoothId(boothId).orElseThrow(FestivalNotFoundForStampException::new);

        if(!booth.isStampEnabled()){ //스탬프 못찍는 부스일 시
            throw new StampNotEnabledException();
        }

        StampRecord stampRecord = stampRecordRepository.findByDeviceIdAndBooth(deviceId, booth).orElse(null);

        if (stampRecord != null) {
            throw new StampAlreadyAddedException();
        }
        if (isMaxStamp(deviceId)){
            throw new StampLimitException();
        }
        stampRecord = new StampRecord(booth, deviceId);
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

    public StampInfoResponse getStampInfo(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        StampInfoResponse stampInfoResponse = new StampInfoResponse(stampInfoRepository.findByFestival(festival));

        return stampInfoResponse;
    }

    public List<BoothResponse> getStampBooth(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundException::new);
        List<BoothResponse> boothResponseList = boothRepository.findBoothsByFestivalAndStampEnabled(festival, true)
                .stream()
                .map(BoothResponse::new)
                .collect(Collectors.toList());

        return boothResponseList;
    }

    public List<Festival> getStampEnabledFestival() {
        List<Festival> festivalList = festivalRepository.findAll();
        List<Festival> filteredFestival = festivalList.stream().filter(f -> f.getStampInfo() != null)
                .collect(Collectors.toList());

        return filteredFestival;
    }

    @Transactional
//    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public Long createStampInfo(StampInfoCreateRequest stampInfoCreateRequest){
//        Booth booth = boothRepository.findByBoothId(stampInfoCreateRequest.getBoothId()).orElseThrow(BoothNotFoundException::new);
        Festival festival = festivalRepository.findById(stampInfoCreateRequest.getFestivalId()).orElseThrow(FestivalNotFoundException::new);

        if(festival.getStampInfo() != null){
            throw new StampInfoAlreadyAdded();
        }
        StampInfo stampInfo = new StampInfo(festival
                , stampInfoCreateRequest.getDefaultImgUrl()
                , stampInfoCreateRequest.getUsedImgUrl());
        festival.setStampInfo(stampInfo);

        return stampInfo.getId();
    }

    @Transactional
//    @CacheEvict(value = "BoothInfo", key = "#boothId")
    public Long deleteStampInfo(Long festivalId){
        Festival festival = festivalRepository.findById(festivalId).orElseThrow(FestivalNotFoundForStampException::new);
        StampInfo stampInfo = festival.getStampInfo();

        if(stampInfo == null){
            throw new StampNotEnabledException();
        }

        festival.setStampInfo(null);
        stampInfoRepository.delete(stampInfo);

        return festival.getId();
    }

    private boolean isMaxStamp(String deviceId){
        List<StampRecord> stampRecords = stampRecordRepository.findByDeviceId(deviceId);

        if(stampRecords.size() >= stampLimit){
            return true;
        }
        return false;
    }
}
