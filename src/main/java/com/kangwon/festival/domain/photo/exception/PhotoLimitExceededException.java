package com.kangwon.festival.domain.photo.exception;

import static com.kangwon.festival.global.exception.Code.PHOTO_UPLOAD_LIMIT;

import com.kangwon.festival.global.exception.BaseException;

public class PhotoLimitExceededException extends BaseException {
    public PhotoLimitExceededException() {super(PHOTO_UPLOAD_LIMIT);}
}
