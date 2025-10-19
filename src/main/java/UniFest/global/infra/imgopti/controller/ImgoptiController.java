package UniFest.global.infra.imgopti.controller;

import UniFest.global.common.response.Response;
import UniFest.global.infra.imgopti.dto.ImgoptiRequest;
import UniFest.global.infra.imgopti.service.ImgoptiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image/opti")
public class ImgoptiController {
    private final ImgoptiService imgoptiService;
    @PostMapping
    public Response<Void> completeOpti(@Valid @RequestBody ImgoptiRequest imgoptiRequest){
        imgoptiService.optimizeImage(imgoptiRequest.getPreImgUrl(), imgoptiRequest.getPostImgUrl());
        return Response.ofSuccess("OK");
    }
}
