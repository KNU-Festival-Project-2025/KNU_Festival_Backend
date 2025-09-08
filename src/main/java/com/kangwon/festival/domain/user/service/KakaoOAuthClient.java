package com.kangwon.festival.domain.user.service;

import com.kangwon.festival.domain.user.exception.KakaoTokenExchangeException;
import com.kangwon.festival.global.annotation.MethodDescription;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {
    private final RestTemplate restTemplate;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";

    @MethodDescription(description = "카카오 엑세스 토큰을 가져옵니다.")
    public String exchangeCodeForAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", clientId);
        form.add("redirect_uri", redirectUri);
        form.add("code", code);

        return Optional.ofNullable(restTemplate.postForEntity(TOKEN_URL, new HttpEntity<>(form, headers), Map.class).getBody())
                .map(m -> m.get("access_token"))
                .map(Object::toString)
                .orElseThrow(KakaoTokenExchangeException::new);
    }
}
