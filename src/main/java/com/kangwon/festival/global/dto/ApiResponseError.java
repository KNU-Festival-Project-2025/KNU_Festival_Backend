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
public class ApiResponseError {

    private Integer code;
    private String message;

    public static ApiResponseError of(Code code) {
        return ApiResponseError.builder()
                .code(code.getCode())
                .message(code.getMessage())
                .build();
    }

    public static ApiResponseError of(Code code, String detailMessage) {
        return ApiResponseError.builder()
                .code(code.getCode())
                .message(detailMessage)
                .build();
    }
}
