package com.web.app.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
    todo Тут короче хочу разобрать все проблемы при логине - UsernameNotFoundException
     и пользователь enabled - false.
 */
@ControllerAdvice
public class ExceptionHandlerController {

/*    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException() {
        //todo
    }*/
}
