package com.web.app.rest;

import com.web.app.entity.AgendaEntity;
import com.web.app.exceptions.UserAlreadyExistsException;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.rest.util.AgendaUtil;
import com.web.app.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

//todo мне не нравится документация ни к одному из контроллеров. Я нигде не написал про диспатчерсервлет,
// что вообще такое сервлет, что такое контейнер сервлетов(томкат) и как оно работает изнутри.
// но расписывать все это дело слишком долго + с ума сойти можно. Как быть?

/**
 * A {@link RestController} class, to handle <pre> GET </pre> and <pre> POST </pre> requests,
 * permitted to <strong>all</strong> users. Mostly for handling requests, related to users.
 */
@RestController
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class UsersManagementController {

    private final UsersService usersService;

    @Autowired
    public UsersManagementController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * This method redirects just logged in user to <pre> success </pre> page.
     *
     * @param user just logged in user.
     * @return <pre> success </pre> page with some parameters.
     */
    @GetMapping("/success")
    public ModelAndView success(@AuthenticationPrincipal UserDetails user) {
        ModelAndView modelAndView = new ModelAndView("success");

        /* Add additional params to 'success' page */
        String username = user.getUsername();
        modelAndView.addObject("username", username);

        Set<AgendaEntity> usersAgendas = usersService
                .loadUserByUsername(username)
                .getAgendas();

        if (usersAgendas == null) {
            usersAgendas = new HashSet<>();
        }

        /* Sort agendas before adding it to modelAndView */
        modelAndView.addObject("agendas", AgendaUtil.sortAgendas(usersAgendas));

        return modelAndView;
    }

    /**
     * This method registers a new user.
     *
     * @param signUpRequest - better see it's javaDoc - {@link SignUpRequestDTO}.
     * @return response message and status code, wrapped in {@link ResponseEntity<String>}.
     */
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody SignUpRequestDTO signUpRequest) {
        try {
            usersService.saveUserInDatabase(signUpRequest);

            return new ResponseEntity<>("You have been successfully registered",
                    HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            log.info("IN {}, registration(@RequestBody SignUpRequestDTO signUpRequest) method," +
                    " a UserAlreadyExistsException occurred", this.getClass());

            return new ResponseEntity<>("Couldn't register your account - " +
                    "user having such email and username already exists",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
