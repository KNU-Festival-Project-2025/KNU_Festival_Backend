package com.kangwon.festival.domain.photo.exception;

import static com.kangwon.festival.global.exception.Code.FORBIDDEN_PHOTO_DELETE;

import com.kangwon.festival.global.exception.BaseException;

public class ForbiddenPhotoDeleteException extends BaseException {
    public ForbiddenPhotoDeleteException() {
        super(FORBIDDEN_PHOTO_DELETE);
    }
}
