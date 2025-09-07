package com.kangwon.festival.domain.user.exception;


import static com.kangwon.festival.global.exception.Code.NOT_FOUND_USER;

import com.kangwon.festival.global.exception.BaseException;

public class NotFoundUserException extends BaseException {
    public NotFoundUserException() {
        super(NOT_FOUND_USER);
    }
}
