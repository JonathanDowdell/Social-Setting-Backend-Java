package com.mettaworldj.socialsetting.server.exception;

import com.mettaworldj.socialsetting.server.dto.error.response.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(SocialSettingException.class)
    public ResponseEntity<Object> handlerSocialSettingException(SocialSettingException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(new Date(), exception.getMessage());
        return new ResponseEntity<>(errorResponseDto, new HttpHeaders(), exception.getHttpStatus());
    }

}
