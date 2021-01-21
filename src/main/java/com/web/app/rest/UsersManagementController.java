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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link RestController} class, to handle <pre> GET </pre> and <pre> POST </pre> requests,
 * permitted to <strong>all</strong> users. Mostly for handling requests, related to users.
 * <p>
 * A {@code Controller} is a {@code component}, handling user's requests.
 * <p>
 * I learned about {@code Controllers} here:
 * https://www.baeldung.com/spring-controllers
 */
@RestController
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class UsersManagementController {

    /**
     * A service, which will be handle user's requests.
     */
    private final UsersService usersService;

    /**
     * Autowire this service.
     * <p>
     * An instance of a class, annotated with
     * <ul>
     *     <li>
     *         {@link Service},
     *     </li>
     *     <li>
     *         {@link org.springframework.stereotype.Repository},
     *     </li>
     * </ul>
     * or any other annotation,
     * which contains a {@link org.springframework.stereotype.Component} inside itself, or even beans from class, annotated with {@link org.springframework.stereotype.Component}
     * will be created and stored in so-called "IOC-container", meaning,
     * {@code Spring} will create an instance of a class,
     * configuring it how by the way (for example, inject another component
     * or inject a field value with the {@link org.springframework.context.annotation.PropertySource} and
     * {@link org.springframework.beans.factory.annotation.Value} annotations) -
     * {@link org.springframework.beans.factory.config.BeanPostProcessor} magic going under the hood
     * (well, not quite magic. If you want to learn more about {@code Spring} internals - better
     * watch some of <i>Eugeniy Borisov's</i> talks on youtube).
     * <strong>only once</strong>,
     * i.e., that instance will be a <strong>singleton</strong> by default,
     * but I can configure this if needed.
     * <p>
     * {@link Autowired} above a method(a constructor as well) means that all method's parameters
     * will be injected from the "IOC-container".
     * <p>
     * <strong>NOTE:</strong> I can also {@code Autowire} components by putting {@link Autowired} above
     * the field I want to {@code Autowire}, but constructor injection is preferred.
     * <p>
     * <strong>NOTE:</strong> prefer inject by interface, not an implementation-class.
     */
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
