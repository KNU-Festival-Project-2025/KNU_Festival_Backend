package com.kangwon.festival.global.exception;

import static com.kangwon.festival.global.exception.Code.INVALID_INPUT;

public class InvalidInputException extends BaseException {

    public InvalidInputException() {
        super(INVALID_INPUT);
    }

    public InvalidInputException(String message) {
        super(INVALID_INPUT, message);
    }
}
