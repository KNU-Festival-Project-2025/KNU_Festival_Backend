package com.kangwon.festival.domain.photo.exception;

import static com.kangwon.festival.global.exception.Code.FILE_UPLOAD_ERROR;

import com.kangwon.festival.global.exception.BaseException;

public class UploadFileException extends BaseException {
    public UploadFileException() {super(FILE_UPLOAD_ERROR);}
}
