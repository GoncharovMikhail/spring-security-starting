package com.web.app.rest;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.exceptions.NoAccessibleAgendasException;
import com.web.app.exceptions.NoAgendasException;
import com.web.app.exceptions.WrongUsernameException;
import com.web.app.rest.util.AgendaUtil;
import com.web.app.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;
import java.util.stream.Collectors;


//todo мне не нравится документация ни к одному из контроллеров. Я нигде не написал про диспатчерсервлет,
// что вообще такое сервлет, что такое контейнер сервлетов(томкат) и как оно работает изнутри.
// но расписывать все это дело слишком долго + с ума сойти можно. Как быть?

/**
 * A {@link RestController} class, to handle <pre> GET </pre> requests,
 * permitted to <strong>all</strong> users.
 */
@RestController
/* @Slf4j generates a logger field(via lombok). */
@Slf4j
public class CommonTemplatesController {

    private final UsersService usersService;

    @Autowired
    public CommonTemplatesController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * This method redirects to the <pre> welcome </pre> page.
     *
     * @return a view and redirects to it.
     */
    @GetMapping("/welcome")
    public ModelAndView welcome() {
        return new ModelAndView("welcome");
    }

    /**
     * This method redirects to the <pre> welcome </pre> page.
     *
     * @return a view and redirects to it.
     */
    @GetMapping("/signup")
    public ModelAndView signup() {
        return new ModelAndView("signup");
    }

    /**
     * This method redirects to the <pre> login </pre> page.
     *
     * @param error in case if {@code /login} <pre> POST </pre> request fails,
     *              (a {@link org.springframework.security.core.userdetails.UsernameNotFoundException},
     *              {@link org.springframework.security.authentication.BadCredentialsException},
     *              or any other type of
     *              {@link org.springframework.security.core.AuthenticationException} occurs),
     *              {@code Spring Security} redirects to the {@code .../login?error} {@code URL}.
     *              Handling these exceptions in {@code Spring Security} is hard,
     *              so I just put a marker param in {@link ModelAndView}, indicating, weather
     *              an exception occurred or not.
     *              More details in {@link templates/login.html}, in a <pre> <script...></script> </pre> there.
     * @return a view name and redirects to it.
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("login");

        if (error != null) {
            modelAndView.addObject("error", true);
            return modelAndView;
        }
        modelAndView.addObject("error", false);

        return modelAndView;
    }

    /**
     * This method returns a view, containing <strong>all</strong> user's <strong>accessible</strong> agendas.
     *
     * @param username specified username.
     * @return a view, containing all user's <strong>accessible</strong> agendas.
     * @throws WrongUsernameException       if user, having specified username is blocked or doesn't exist.
     * @throws NoAgendasException           if user has no agendas yet.
     * @throws NoAccessibleAgendasException if user has no <strong>accessible</strong> agendas.
     */
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam("username") String username) throws WrongUsernameException,
            NoAgendasException, NoAccessibleAgendasException {
        /* Find a user by username. */
        final UsersEntity user = usersService.loadUserByUsername(username);

        /* Throw an exception if user, having specified username is blocked or doesn't exist. */
        if (user == null || !user.isEnabled()) {
            log.info("user having username: '{}' not found or banned", username);
            throw new WrongUsernameException("user having username: '" + username + "' not found or banned");
        }

        /* If user has no agendas, then throw a NoAgendasException. */
        Set<AgendaEntity> agendas = user.getAgendas();
        if (agendas.isEmpty()) {
            log.info("user having username: '{}' has no agendas yet", username);
            throw new NoAgendasException("user having username: '" + username + "' has no agendas yet");
        }

        /* Filter all accessible agendas and collect them to set. */
        Set<AgendaEntity> accessibleAgendas = agendas.stream()
                .filter(AgendaEntity::isAccessible)
                .collect(Collectors.toSet());

        /* If there are no accessible agendas - throw an NoAccessibleAgendasException. */
        if (accessibleAgendas.isEmpty()) {
            log.info("user having username: '{}' has no accessible agendas yet", username);
            throw new NoAccessibleAgendasException("user having username: '" + username +
                    "' has no accessible agendas yet");
        }

        /* Create an instance of a view page. */
        ModelAndView modelAndView = new ModelAndView("search");

        /* Add parameters to this page. */
        modelAndView.addObject("username", username);
        modelAndView.addObject("accessibleAgendas", AgendaUtil.sortAgendas(accessibleAgendas));

        return modelAndView;
    }
}

