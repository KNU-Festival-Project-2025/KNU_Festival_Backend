package com.kangwon.festival.domain.user.exception;

import static com.kangwon.festival.global.exception.Code.KAKAO_INVALID_TOKEN;

import com.kangwon.festival.global.exception.BaseException;

public class KakaoTokenExchangeException extends BaseException {
    public KakaoTokenExchangeException() {
        super(KAKAO_INVALID_TOKEN);
    }
}