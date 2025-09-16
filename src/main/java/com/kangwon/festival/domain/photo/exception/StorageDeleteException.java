package com.kangwon.festival.domain.photo.exception;

import static com.kangwon.festival.global.exception.Code.STORAGE_DELETE_ERROR;

import com.kangwon.festival.global.exception.BaseException;

public class StorageDeleteException extends BaseException {
    public StorageDeleteException() {super(STORAGE_DELETE_ERROR);}
}
