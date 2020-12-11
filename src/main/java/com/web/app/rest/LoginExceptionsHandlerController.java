package com.web.app.rest;

import com.web.app.service.exceptions.WrongUsernameException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LoginExceptionsHandlerController {

    //todo эксепшнхендлер для неправильного юзернейма - хорошо бы просто алерт вывести
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleBadCredentialsException(UsernameNotFoundException usernameNotFoundException) {

    }

    //todo эксепшнхендлер для неправильного пароля - хорошо бы просто алерт вывести
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException badCredentialsException) {

    }

    //todo эксепшнхендлер для дизейблд аккаунта - хорошо бы просто алерт вывести
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabledException(DisabledException disabledException) {

    }

    //todo эксепшнхендлер для вронг юзернейма - хорошо бы просто алерт вывести
    @ExceptionHandler(WrongUsernameException.class)
    public ResponseEntity<?> handleDisabledException(WrongUsernameException wrongUsernameException) {

    }
}
