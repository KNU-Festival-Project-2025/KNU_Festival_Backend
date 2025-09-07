package com.kangwon.festival.domain.user.dto.response;

import com.kangwon.festival.domain.user.entity.User;
import lombok.Builder;

@Builder
public record KaKaoUserResponse (
        String id,
        String profileImgUrl
)
{
    public static KaKaoUserResponse of(User user) {
        return KaKaoUserResponse.builder()
                .id(user.getKakaoId())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
    }
}