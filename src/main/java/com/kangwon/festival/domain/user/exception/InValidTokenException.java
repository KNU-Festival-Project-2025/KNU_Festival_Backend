package com.kangwon.festival.domain.user.exception;

import static com.kangwon.festival.global.exception.Code.INVALID_TOKEN;

import com.kangwon.festival.global.exception.BaseException;

public class InValidTokenException extends BaseException {
    public InValidTokenException(String message) {
        super(INVALID_TOKEN ,message);
    }
}