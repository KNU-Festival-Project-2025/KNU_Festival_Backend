package com.kangwon.festival.domain.user.exception;

import static com.kangwon.festival.global.exception.Code.DUPLICATE_NICKNAME;

import com.kangwon.festival.global.exception.BaseException;

public class DuplicateNicknameException extends BaseException {
    public DuplicateNicknameException() {
        super(DUPLICATE_NICKNAME);
    }
}