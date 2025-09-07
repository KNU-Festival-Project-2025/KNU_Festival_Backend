package com.kangwon.festival.domain.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kangwon.festival.global.annotation.MethodDescription;
import com.kangwon.festival.global.exception.InternalServerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.JsonArray;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String KAKAO_ME_URL = "https://kapi.kakao.com/v2/user/me";

    @MethodDescription(description = "카카오에서 유저 정보를 가져옵니다.")
    public String getKakaoData(String kakaoAccessToken) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + kakaoAccessToken);
            HttpEntity httpEntity = new HttpEntity<JsonArray>(headers);
            ResponseEntity<Object> responseData = restTemplate.exchange(KAKAO_ME_URL, HttpMethod.GET, httpEntity, Object.class);
            return objectMapper.convertValue(responseData.getBody(), Map.class).get("id").toString();
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }
}
