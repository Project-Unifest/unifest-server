package UniFest.auth.controller;

import UniFest.auth.service.AuthService;
import UniFest.dto.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "사용자 로그아웃 로그인 : /login")
    @GetMapping("/logout")
    public Response logout(@RequestHeader(value = "RefreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return Response.ofSuccess("OK",null);
    }

    @Operation(summary = "액세스토큰 재발급")
    @GetMapping("/reissue")
    public ResponseEntity<Void> reissue(@RequestHeader(value = "RefreshToken") String refreshToken) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.reissue(refreshToken))
                .build();
    }
}
