package com.kangwon.festival.domain.user.exception;

import static com.kangwon.festival.global.exception.Code.EXPIRED_TOKEN;

import com.kangwon.festival.global.exception.BaseException;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException(String message) {
        super(EXPIRED_TOKEN, message);
    }
}