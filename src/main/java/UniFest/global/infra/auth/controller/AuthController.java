package UniFest.global.infra.auth.controller;

import UniFest.global.infra.auth.dto.request.LoginRequest;
import UniFest.global.infra.auth.service.AuthService;
import UniFest.global.common.response.Response;
import com.google.api.client.auth.oauth2.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증", description = "로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 로그아웃")
    @GetMapping("/logout")
    public Response<Void> logout(@RequestHeader(value = "RefreshToken") String refreshToken) {
        authService.logout(refreshToken);
        return Response.ofSuccess("OK");
    }

    @Operation(summary = "액세스토큰 재발급")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "만료된 리프레시 토큰입니다. (RefreshTokenExpiredException) [에러 코드: 2003]")
    })
    @PostMapping("/reissue")
    public ResponseEntity<Void> reissue(@RequestHeader(value = "RefreshToken") String refreshToken) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.reissue(refreshToken))
                .build();
    }
}
