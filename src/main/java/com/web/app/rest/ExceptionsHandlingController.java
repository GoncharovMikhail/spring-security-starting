package com.web.app.rest;

import com.web.app.exceptions.NoAccessibleAgendasException;
import com.web.app.exceptions.NoAgendasException;
import com.web.app.exceptions.WrongUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


//todo мне не нравится документация ни к одному из контроллеров. Я нигде не написал про диспатчерсервлет,
// что вообще такое сервлет, что такое контейнер сервлетов(томкат) и как оно работает изнутри.
// но расписывать все это дело слишком долго + с ума сойти можно. Как быть?

/**
 * This controller handles exceptions, which occurred in other controller's methods.
 */
@ControllerAdvice
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class ExceptionsHandlingController {

    /**
     * Handle {@link WrongUsernameException} exception - return an <pre> 'exception' </pre> page,
     * where will be displayed: "Oops! You entered the wrong username.".
     *
     * @param wrongUsernameException exception to handle.
     * @return return an <pre> 'exception' </pre> page.
     * @see AdminController#ban(Map)
     */
    @ExceptionHandler(WrongUsernameException.class)
    public ModelAndView handleWrongUsernameException(WrongUsernameException wrongUsernameException) {
        log.info("IN {}, handling {}", this.getClass(), String.valueOf(wrongUsernameException));

        ModelAndView modelAndView = new ModelAndView("exception");

        modelAndView.addObject("message", "Oops! You entered the wrong username");

        return modelAndView;
    }

    /**
     * Handle {@link NoAgendasException} exception - return an <pre> 'exception' </pre> page,
     * where will be displayed: "This user has no agendas yet".
     *
     * @param noAgendaException exception to handle.
     * @return return an <pre> 'exception' </pre> page.
     * @see CommonTemplatesController#search(String)
     */
    @ExceptionHandler(NoAgendasException.class)
    public ModelAndView handleNoAgendaException(NoAgendasException noAgendaException) {
        log.info("IN {}, handling {}", this.getClass(), String.valueOf(noAgendaException));

        ModelAndView modelAndView = new ModelAndView("exception");

        modelAndView.addObject("message", "This user has no agendas yet");

        return modelAndView;
    }

    /**
     * Handle {@link NoAccessibleAgendasException} exception - return an <pre> 'exception' </pre> page,
     * where will be displayed: "This user has no accessible agendas yet".
     *
     * @param noAccessibleAgendasException exception to handle.
     * @return return an <pre> 'exception' </pre> page.
     * @see CommonTemplatesController#search(String)
     */
    @ExceptionHandler(NoAccessibleAgendasException.class)
    public ModelAndView handleNoAgendaException(NoAccessibleAgendasException noAccessibleAgendasException) {
        log.info("IN {}, handling {}", this.getClass(), String.valueOf(noAccessibleAgendasException));

        ModelAndView modelAndView = new ModelAndView("exception");

        modelAndView.addObject("message", "This user has no accessible agendas yet");

        return modelAndView;
    }
}
