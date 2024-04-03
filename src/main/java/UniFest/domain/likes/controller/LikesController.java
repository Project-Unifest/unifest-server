package UniFest.domain.likes.controller;

import UniFest.domain.likes.service.LikesService;
import UniFest.dto.request.likes.PostLikesRequest;
import UniFest.dto.response.Response;
import UniFest.dto.response.booth.BoothDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return Response.ofSuccess("OK", retId);
    }

}