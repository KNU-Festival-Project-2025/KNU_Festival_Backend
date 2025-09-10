package com.kangwon.festival.domain.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kangwon.festival.domain.user.dto.KaKaoUserResponse;
import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.exception.InternalServerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String KAKAO_ME_URL = "https://kapi.kakao.com/v2/user/me";

    @MethodDescription(description = "카카오에서 유저 정보를 가져옵니다.")
    public KaKaoUserResponse getKakaoData(String kakaoAccessToken) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + kakaoAccessToken);
            HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<Object> responseData =
                    restTemplate.exchange(KAKAO_ME_URL, HttpMethod.GET, httpEntity, Object.class);

            Map<String, Object> body = objectMapper.convertValue(responseData.getBody(), Map.class);

            String id = body.get("id").toString();

            Map<String, Object> properties = (Map<String, Object>) body.get("properties");
            String profileImgUrl = properties != null ? (String) properties.get("profile_image") : null;

            return KaKaoUserResponse.builder()
                    .id(id)
                    .profileImgUrl(profileImgUrl)
                    .build();
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }
}
