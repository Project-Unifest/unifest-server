package UniFest.auth.controller;

import UniFest.auth.service.AuthService;
import UniFest.dto.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @GetMapping("/logout")
    public Response logout(@RequestHeader(value = "RefreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return Response.ofSuccess("OK",null);
    }

    // AccessToken 재발급 - refresh 토큰 확인
    @GetMapping("/reissue")
    public ResponseEntity<Void> reissue(@RequestHeader(value = "RefreshToken") String refreshToken) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.reissue(refreshToken))
                .build();
    }
}
