package UniFest.domain.likes.service;

import UniFest.domain.booth.entity.Booth;
import UniFest.domain.booth.repository.BoothRepository;
import UniFest.domain.festival.entity.Festival;
import UniFest.domain.festival.repository.FestivalRepository;
import UniFest.domain.likes.entity.Likes;
import UniFest.domain.likes.repository.LikesRepository;
import UniFest.dto.response.booth.BoothDetailResponse;
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
    private final FestivalRepository festivalRepository;

    @Transactional
    public Long likeBooth(Long boothId, String token) {
        // Like 생성
        Long retId = null;
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
            throw new RuntimeException("이미 좋아요를 누른 부스입니다.");
        }
         return retId;
    }


}
