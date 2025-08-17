package UniFest.domain.home.controller;

import UniFest.domain.home.dto.request.HomeCardCreateRequest;
import UniFest.domain.home.dto.response.HomeInfoResponse;
import UniFest.domain.home.service.HomeService;
import UniFest.global.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;
    @GetMapping("/info")
    public Response<HomeInfoResponse> getHomeInfo(){
        return Response.ofSuccess("OK", homeService.getHomeInfo());
    }

    @PostMapping("")
    public Response<Long> createHomeCard(@RequestBody HomeCardCreateRequest request){
        return Response.ofCreated("Created", homeService.createHomeCard(request));
    }

    @DeleteMapping("card/{id}")
    public Response<Void> deleteHomeCard(@PathVariable Long id){
        if(!homeService.existsById(id)){
            return Response.ofNotFound("존재하지 않는 카드뉴스입니다", null);
        }
        homeService.deleteHomeCardById(id);
        return Response.ofSuccess("Deleted", null);
    }
}