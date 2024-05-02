package UniFest.domain.likes.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.likes.entity.Likes;
import UniFest.domain.likes.repository.LikesRepository;
import UniFest.exception.booth.BoothNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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


}
