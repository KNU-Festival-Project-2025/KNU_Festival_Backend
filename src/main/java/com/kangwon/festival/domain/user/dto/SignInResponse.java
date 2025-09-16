package com.kangwon.festival.domain.user.dto;

import com.kangwon.festival.domain.security.dto.Token;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record SignInResponse(
        @NonNull String accessToken,
        @NonNull String refreshToken
) {
    public static SignInResponse from(Token token) {
        return SignInResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
