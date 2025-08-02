package com.kangwon.festival.global.exception;

import static com.kangwon.festival.global.exception.Code.INVALID_TOKEN;

import lombok.Getter;

@Getter
public class InvalidTokenUser extends BaseException {
    public InvalidTokenUser() {
        super(INVALID_TOKEN);
    }
}
