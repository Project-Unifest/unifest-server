package UniFest.domain.home.service;

import UniFest.domain.home.dto.request.HomeCardCreateRequest;
import UniFest.domain.home.dto.response.HomeInfoResponse;
import UniFest.domain.home.entity.HomeCard;
import UniFest.domain.home.entity.HomeTip;
import UniFest.domain.home.repository.HomeCardRepository;
import UniFest.domain.home.repository.HomeTipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {
    private final HomeCardRepository homeCardRepository;
    private final HomeTipRepository homeTipRepository;
    public HomeInfoResponse getHomeInfo() {
        List<HomeCard> homeCards = homeCardRepository.findAll();
        List<HomeTip> homeTips = homeTipRepository.findAll();

        return HomeInfoResponse.builder()
                .homeCardList(homeCards)
                .homeTipList(homeTips)
                .build();
    }

    @Transactional
    public Long createHomeCard(HomeCardCreateRequest request){
        HomeCard homeCard = new HomeCard(request.getThumbnailImgUrl(), request.getDetailImgUrl());
        return homeCardRepository.save(homeCard).getId();
    }

    @Transactional
    public void deleteHomeCardById(Long id){
        homeCardRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public boolean existsById(Long id){return homeCardRepository.existsById(id);}
}
