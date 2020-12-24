package com.mettaworldj.socialsetting.server.exception;

import org.springframework.http.HttpStatus;

public class SocialSettingException extends RuntimeException {

    private final HttpStatus httpStatus;

    public SocialSettingException(String exMessage, Exception exception, HttpStatus httpStatus) {
        super(exMessage, exception);
        this.httpStatus = httpStatus;
    }

    public SocialSettingException(String exMessage, HttpStatus httpStatus) {
        super(exMessage);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}