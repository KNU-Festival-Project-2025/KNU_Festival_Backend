package com.kangwon.festival.domain.photo.exception;


import static com.kangwon.festival.global.exception.Code.INVALID_FILE_TYPE;

import com.kangwon.festival.global.exception.BaseException;import com.kangwon.festival.global.exception.Code;

public class InvalidFileTypeException extends BaseException {
    public InvalidFileTypeException() {super(INVALID_FILE_TYPE);}
}
