package com.kangwon.festival.global.dto;

import com.kangwon.festival.global.exception.Code;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ApiResponseMessage {

    private final Integer code = Code.SUCCESS.getCode();
    private String message = Code.SUCCESS.getMessage(); // default message

    public static ApiResponseMessage of(String message) {
        return ApiResponseMessage.builder()
                .message(message)
                .build();
    }
}
