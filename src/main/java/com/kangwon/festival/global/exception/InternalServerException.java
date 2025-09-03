package com.kangwon.festival.global.exception;

import static com.kangwon.festival.global.exception.Code.INTERNAL_SERVER_ERROR;

public class InternalServerException extends BaseException{
    public InternalServerException() {
        super(INTERNAL_SERVER_ERROR);
    }
}
