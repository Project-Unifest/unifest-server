package UniFest.domain.booth.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.booth.entity.Likes;
import UniFest.domain.booth.repository.LikesRepository;
import UniFest.dto.response.booth.LikedBoothResponse;
import UniFest.exception.booth.BoothNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {


    private final LikesRepository likesRepository;
    private final BoothRepository boothRepository;

    @Transactional
    public Long likeBooth(Long boothId, String token) {
        // Like 생성
        Long retId = -1L;
        Booth booth = boothRepository.findById(boothId)
                .filter(Booth::isEnabled)
                .orElseThrow(BoothNotFoundException::new);

        Likes existLike = likesRepository.findByBoothIdAndToken(boothId, token);
        System.out.println("existLike = " + existLike);
        if(existLike==null){
            Likes newLike = Likes.builder()
                    .booth(booth)
                    .token(token)
                    .build();
            retId = likesRepository.save(newLike).getId();
        }
        else {
            likesRepository.delete(existLike);
        }
         return retId;
    }

    public int getLikes(Long boothId) {
        Booth booth = boothRepository.findById(boothId)
                .filter(Booth::isEnabled)
                .orElseThrow(BoothNotFoundException::new);
        return booth.getLikesCount();

    }

    public List<LikedBoothResponse> getLikedBooths(String token){
        List<LikedBoothResponse> boothResponses = new ArrayList<>();
        List<Likes> likesList = likesRepository.findAllLikesByToken(token);
        List<Booth> booths = boothRepository.findBoothsByIdIn(
                likesList.stream().map(like -> like.getBooth().getId()).toList()
        );
        for(Booth booth : booths){
            boothResponses.add(new LikedBoothResponse(booth));
        }
        return boothResponses;
    }

}
