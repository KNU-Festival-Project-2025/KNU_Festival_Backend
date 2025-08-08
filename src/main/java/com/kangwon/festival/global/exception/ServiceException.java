package com.kangwon.festival.global.exception;


public class ServiceException extends BaseException {

    public ServiceException(Code code) {
        super(code);
    }

    public ServiceException(Code code, String message) {
        super(code, message);
    }

}
