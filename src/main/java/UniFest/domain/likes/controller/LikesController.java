package UniFest.domain.likes.controller;

import UniFest.domain.likes.service.LikesService;
import UniFest.dto.request.likes.PostLikesRequest;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/likes")
public class LikesController {


    private final LikesService likesService;
    @Operation(summary = "좋아요 생성")
    @PostMapping
    public Response<Long> postLike( @RequestBody PostLikesRequest postLikeRequest) {
        log.debug("[LikesController.postLike]");
        Long retId = likesService.likeBooth(postLikeRequest.getBoothId(), postLikeRequest.getToken());
        if(retId==-1){
            return Response.ofSuccess("Delete Done", retId);
        }
        else{
            return Response.ofSuccess("OK", retId);
        }
    }
    @Operation(summary = "좋아요 조회")
    @GetMapping("/{booth-id}")
    public Response<Long> getLike(@PathVariable("booth-id") Long boothId) {
        log.debug("[LikesController.getLike]");
        int likesCount = likesService.getLikes(boothId);
        return Response.ofSuccess("해당 부스에 대한 좋아요 수 조회", (long)likesCount);
    }
}
