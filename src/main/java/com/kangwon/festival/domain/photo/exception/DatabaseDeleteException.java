package com.kangwon.festival.domain.photo.exception;

import static com.kangwon.festival.global.exception.Code.DELETE_ERROR;

import com.kangwon.festival.global.exception.BaseException;

public class DatabaseDeleteException extends BaseException {
    public DatabaseDeleteException () {super(DELETE_ERROR);}
}
