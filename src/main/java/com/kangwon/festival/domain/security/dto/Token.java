package com.kangwon.festival.domain.security.dto;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record Token(
        @NotNull @Length(max = 500) String accessToken,
        @NotNull @Length(max = 500) String refreshToken
) {
}

