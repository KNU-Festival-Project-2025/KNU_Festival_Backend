package com.kangwon.festival.domain.user.controller;

import com.kangwon.festival.domain.security.dto.CustomUserDetails;
import com.kangwon.festival.domain.user.dto.LoginResponse;
import com.kangwon.festival.domain.user.dto.SignInRequest;
import com.kangwon.festival.domain.user.dto.SignInResponse;
import com.kangwon.festival.domain.user.service.AuthService;
import com.kangwon.festival.domain.user.service.KakaoOAuthClient;
import com.kangwon.festival.global.annotation.CurrentUser;
import com.kangwon.festival.global.annotation.UserOnly;
import com.kangwon.festival.global.dto.ApiResponseData;
import com.kangwon.festival.global.dto.ApiResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final KakaoOAuthClient kakaoOAuthClient;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseData> exchange(@Valid @RequestBody SignInRequest request) {
        LoginResponse response = authService.signIn(request);

        return ResponseEntity.ok()
                .header("X-Access-Token",  response.accessToken())
                .header("X-Refresh-Token", response.refreshToken())
                .header("Access-Control-Expose-Headers", "X-Access-Token, X-Refresh-Token")
                .body(ApiResponseData.of(response, "정상적으로 로그인되었습니다."));
    }

    @UserOnly
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseMessage> signOut(@CurrentUser CustomUserDetails user) {
        authService.signOut(user);
        return ResponseEntity.ok(ApiResponseMessage.of("로그아웃에 성공하였습니다."));
    }

    @UserOnly
    @DeleteMapping
    public ResponseEntity<ApiResponseMessage> withdraw(@CurrentUser CustomUserDetails user) {
        authService.withdraw(user);
        return ResponseEntity.ok(ApiResponseMessage.of("회원이 탈퇴되었습니다."));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponseData> reissue(@RequestHeader("X-Refresh-Token") String refreshToken) {

        SignInResponse response = authService.reissue(refreshToken);

        return ResponseEntity.ok()
                .header("X-Access-Token",  response.accessToken())
                .header("X-Refresh-Token", response.refreshToken())
                .header("Access-Control-Expose-Headers", "X-Access-Token, X-Refresh-Token")
                .body(ApiResponseData.of(response, "토큰이 재발급되었습니다."));
    }
}
