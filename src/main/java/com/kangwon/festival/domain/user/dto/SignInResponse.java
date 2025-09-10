package com.kangwon.festival.domain.user.dto;

import com.kangwon.festival.domain.security.dto.Token;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record SignInResponse(
        @NonNull String accessToken,
        @NonNull String refreshToken
) {
    public static SignInResponse of(Token token) {
        return SignInResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
