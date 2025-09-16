package com.kangwon.festival.domain.photo.exception;

import static com.kangwon.festival.global.exception.Code.NOT_FOUND_PHOTO;

import com.kangwon.festival.global.exception.BaseException;

public class NotFoundPhotoException extends BaseException {
    public NotFoundPhotoException() {super(NOT_FOUND_PHOTO);};
}
