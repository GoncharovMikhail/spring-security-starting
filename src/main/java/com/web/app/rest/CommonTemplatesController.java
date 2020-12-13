package com.web.app.rest;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.rest.util.AgendaUtil;
import com.web.app.service.UsersService;
import com.web.app.exceptions.WrongUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class CommonTemplatesController {

    private final UsersService usersService;

    @Autowired
    public CommonTemplatesController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

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

    //todo сделать редирект. Оно так-то все работает.
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam("username") String username) throws WrongUsernameException {
        ModelAndView modelAndView = new ModelAndView("search");

        UsersEntity user = usersService.loadUserByUsername(username);
        if (user == null || !user.isEnabled()) {
            throw new WrongUsernameException(username + " not found");
        }

        Set<AgendaEntity> agendas = user.getAgendas();
        if (user.getAgendas() == null) {
            agendas = new HashSet<>();
        }

        Set<AgendaEntity> accessibleAgendas = agendas.stream()
                .filter(AgendaEntity::isAccessible)
                .collect(Collectors.toSet());

        modelAndView.addObject("username", username);
        modelAndView.addObject("accessibleAgendas", AgendaUtil.sortAgendas(accessibleAgendas));

        return modelAndView;
    }
}

