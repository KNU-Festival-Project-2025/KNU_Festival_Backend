package com.kangwon.festival.domain.user.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {
    @Column(nullable = false, length = 500)
    private String accessToken;

    @Column(nullable = false, length = 500)
    private String refreshToken;
}

