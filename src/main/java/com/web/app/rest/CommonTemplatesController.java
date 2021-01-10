package com.web.app.rest;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.exceptions.WrongUsernameException;
import com.web.app.rest.util.AgendaUtil;
import com.web.app.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link Controller} class, to handle <pre> GET </pre> requests,
 * permitted to <strong>all</strong> users.
 */
@Controller
@Slf4j
public class CommonTemplatesController {

    private final UsersService usersService;

    @Autowired
    public CommonTemplatesController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * This method redirects to the <pre> welcome </pre> page. Some thymeleaf magic
     * going under the hood: how a string transforms into view's name.
     *
     * @return a view name and redirects to it.
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * This method redirects to the <pre> welcome </pre> page. Some thymeleaf magic
     * going under the hood: how a string transforms into view's name.
     *
     * @return a view name and redirects to it.
     */
    @GetMapping("/signup")
    public String signup() {
        return "signup";
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
     * @throws WrongUsernameException if user, having specified username is blocked or doesn't exist.
     */
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam("username") String username) throws WrongUsernameException {
        ModelAndView modelAndView = new ModelAndView("search");

        /* Find a user by username*/
        UsersEntity user = usersService.loadUserByUsername(username);

        /* Throw an exception if user, having specified username is blocked or doesn't exist. */
        if (user == null || !user.isEnabled()) {
            log.info("user having username: '{}' not found or banned", username);
            throw new WrongUsernameException("user having username: '" + username + "' not found or banned");
        }

        /* If user has no agendas, then return an empty set. */
        Set<AgendaEntity> agendas = user.getAgendas();
        if (user.getAgendas() == null) {
            agendas = new HashSet<>();
        }

        /* Filter all accessible agendas and collect them to set. */
        Set<AgendaEntity> accessibleAgendas = agendas.stream()
                .filter(AgendaEntity::isAccessible)
                .collect(Collectors.toSet());

        /* Add parameters to this page. */
        modelAndView.addObject("username", username);
        modelAndView.addObject("accessibleAgendas", AgendaUtil.sortAgendas(accessibleAgendas));

        return modelAndView;
    }
}

