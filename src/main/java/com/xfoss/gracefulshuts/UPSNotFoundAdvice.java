package com.xfoss.gracefulshuts;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class UPSNotFoundAdvice {
    
    @ResponseBody
    @ExceptionHandler(UPSNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String UPSNotFoundHandler(UPSNotFoundException ex) {
        return ex.getMessage();
    }
}
