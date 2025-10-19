package UniFest.global.infra.imgopti.service;

import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.home.repository.HomeCardRepository;
import UniFest.domain.menu.repository.MenuRepository;
import UniFest.domain.school.repository.SchoolRepository;
import UniFest.domain.star.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ImgoptiService {
    
    private final BoothRepository boothRepository;
    private final FestivalRepository festivalRepository;
    private final HomeCardRepository homeCardRepository;
    private final MenuRepository menuRepository;
    private final SchoolRepository schoolRepository;
    private final StarRepository starRepository;

    public void optimizeImage(String preImgUrl, String postImgUrl){
        // Booth 관련 이미지 업데이트
        updateBoothImages(preImgUrl, postImgUrl);
        
        // Festival 관련 이미지 업데이트
        updateFestivalImages(preImgUrl, postImgUrl);
        
        // HomeCard 관련 이미지 업데이트
        updateHomeCardImages(preImgUrl, postImgUrl);
        
        // Menu 관련 이미지 업데이트
        updateMenuImages(preImgUrl, postImgUrl);
        
        // School 관련 이미지 업데이트
        updateSchoolImages(preImgUrl, postImgUrl);
        
        // Star 관련 이미지 업데이트
        updateStarImages(preImgUrl, postImgUrl);
    }
    
    private void updateBoothImages(String preImgUrl, String postImgUrl) {
        // Booth의 thumbnail 업데이트
        boothRepository.updateThumbnail(preImgUrl, postImgUrl);
    }
    
    private void updateFestivalImages(String preImgUrl, String postImgUrl) {
        // Festival의 thumbnail 업데이트
        festivalRepository.updateThumbnail(preImgUrl, postImgUrl);
    }
    
    private void updateHomeCardImages(String preImgUrl, String postImgUrl) {
        // HomeCard의 thumbnailImgUrl 업데이트
        homeCardRepository.updateThumbnailImgUrl(preImgUrl, postImgUrl);
        
        // HomeCard의 detailImgUrl 업데이트
        homeCardRepository.updateDetailImgUrl(preImgUrl, postImgUrl);
    }
    
    private void updateMenuImages(String preImgUrl, String postImgUrl) {
        // Menu의 imgUrl 업데이트
        menuRepository.updateImgUrl(preImgUrl, postImgUrl);
    }
    
    private void updateSchoolImages(String preImgUrl, String postImgUrl) {
        // School의 thumbnail 업데이트
        schoolRepository.updateThumbnail(preImgUrl, postImgUrl);
    }
    
    private void updateStarImages(String preImgUrl, String postImgUrl) {
        // Star의 img 업데이트
        starRepository.updateImg(preImgUrl, postImgUrl);
    }
}
