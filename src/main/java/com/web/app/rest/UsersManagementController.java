package com.web.app.rest;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.util.AgendaUtil;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.service.UsersService;
import com.web.app.service.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
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

        Set<AgendaEntity> USERZ_AGENDAS;

        if (usersService.loadUserByUsername(USERNAME).getAgendas() != null) {
            USERZ_AGENDAS = usersService.loadUserByUsername(USERNAME).getAgendas();
        } else {
            USERZ_AGENDAS = new HashSet<>();
        }

        /* Sort agendas before adding it to modelAndView */
        modelAndView.addObject("agendas", AgendaUtil.sortAgendas(USERZ_AGENDAS));

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
