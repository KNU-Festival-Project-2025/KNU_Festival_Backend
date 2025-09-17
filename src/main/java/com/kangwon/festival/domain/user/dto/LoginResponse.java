package com.kangwon.festival.domain.user.dto;

import com.kangwon.festival.domain.security.dto.Token;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record LoginResponse(
        @NonNull String accessToken,
        @NonNull String refreshToken,
        @NonNull String nickname
) {
    public static LoginResponse from(Token token, String nickname) {
        return LoginResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .nickname(nickname)
                .build();
    }
}
