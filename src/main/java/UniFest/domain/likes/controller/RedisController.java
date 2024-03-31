package UniFest.domain.likes.controller;

import UniFest.dto.request.likes.PostLikesRequest;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, String> redisTemplate;

    @Operation(summary = "Redis에 토큰 저장")
    @PostMapping("/test")
    public Response<Long> addRedisTest(@RequestBody PostLikesRequest postLikesRequest) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set(postLikesRequest.getBoothId().toString(), postLikesRequest.getToken());
        return Response.ofSuccess("OK", postLikesRequest.getBoothId());
    }

    @Operation(summary = "ID로부터 토큰을 획득")
    @GetMapping("/test")
    public Response<String> getRedisTest(@RequestBody PostLikesRequest postLikesRequest) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String token = vop.get(postLikesRequest.getBoothId().toString());
        return Response.ofSuccess("OK", token);
    }

}
