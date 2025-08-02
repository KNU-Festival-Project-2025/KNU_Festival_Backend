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
public class ApiResponseData<T> {

    private final Integer code = Code.SUCCESS.getCode();
    private String message = Code.SUCCESS.getMessage(); // default message
    private T data;

    public static <T> ApiResponseData<T> of(T data, String message) {
        return ApiResponseData.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}
