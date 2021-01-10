package com.web.app.rest;

import com.web.app.exceptions.WrongUsernameException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * This controller handles exceptions, which occurred in other controller's methods.
 */
@ControllerAdvice
public class ExceptionsHandlingController {

    /**
     * Handle {@link WrongUsernameException} exception - return an <pre> 'exception' </pre>,
     * where will be displayed: "Oops! You entered the wrong username.".
     *
     * @param wrongUsernameException exception to handle.
     * @return return an <pre> 'exception' </pre>.
     * @see AdminController#ban(Map)
     */
    @ExceptionHandler(WrongUsernameException.class)
    public ModelAndView handleWrongUsernameException(WrongUsernameException wrongUsernameException) {
        ModelAndView modelAndView = new ModelAndView("exception");

        modelAndView.addObject("message", "Oops! You entered the wrong username.");

        return modelAndView;
    }
}
