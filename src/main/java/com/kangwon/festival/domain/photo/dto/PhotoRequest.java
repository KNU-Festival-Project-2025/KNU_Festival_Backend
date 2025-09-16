package com.kangwon.festival.domain.photo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PhotoRequest (
        @NotBlank(message = "닉네임을 입력해 주세요.")
        @Size(max = 5, message = "닉네임은 5글자까지 입력 가능합니다.")
        String nickname,
        @NotBlank(message = "내용을 입력해 주세요.")
        @Size(max = 50, message = "내용은 최대 50자까지 입력 가능합니다.")
        String content
){}
