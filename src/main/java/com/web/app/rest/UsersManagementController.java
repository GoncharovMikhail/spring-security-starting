package com.web.app.rest;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.rest.util.AgendaUtil;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.service.UsersService;
import com.web.app.service.exceptions.UserAlreadyExistsException;
import com.web.app.service.exceptions.WrongUsernameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class UsersManagementController {

    private final UsersService usersService;

    @Autowired
    public UsersManagementController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/success")
    public ModelAndView success(@AuthenticationPrincipal UserDetails user) {

        ModelAndView modelAndView = new ModelAndView("success");

        /* Add additional params to 'success' page */
        final String USERNAME = user.getUsername();
        modelAndView.addObject("username", USERNAME);

        Set<AgendaEntity> USERS_AGENDAS;

        if (usersService.loadUserByUsername(USERNAME).getAgendas() != null) {
            USERS_AGENDAS = usersService.loadUserByUsername(USERNAME).getAgendas();
        } else {
            USERS_AGENDAS = new HashSet<>();
        }

        /* Sort agendas before adding it to modelAndView */
        modelAndView.addObject("agendas", AgendaUtil.sortAgendas(USERS_AGENDAS));

        return modelAndView;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody SignUpRequestDTO signUpRequest) {
        try {
            usersService.saveUserInDatabase(signUpRequest);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
