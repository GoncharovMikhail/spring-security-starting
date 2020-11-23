package com.web.app.rest;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.util.AgendaUtil;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.security.UsersDetails;
import com.web.app.service.UsersService;
import com.web.app.service.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class UsersManagementController {

    private final UsersService usersService;

    @Autowired
    public UsersManagementController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/success")
    public ModelAndView success(@AuthenticationPrincipal UsersDetails user) {

        ModelAndView modelAndView = new ModelAndView("success");

        /* Add additional params to 'success' page */
        final String USERNAME = user.getUsername();
        modelAndView.addObject("username", USERNAME);

        //TODO подумать насчет кеша.
        Set<AgendaEntity> USERZ_AGENDAS = usersService
                .loadUserByUsername(USERNAME)
                .getAgendas();

        /* Sort agendas before adding it to modelAndView */
        modelAndView.addObject("agendas", AgendaUtil.sortAgendas(USERZ_AGENDAS));

        return modelAndView;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody SignUpRequestDTO signUpRequest) {
        /* In this 'try'-block we'll attempt to save user in database, if it's processed fine,
         * then we redirect to login form. */
        try {
            usersService.saveUserInDatabase(signUpRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>("UserAlreadyExistsException", HttpStatus.BAD_REQUEST);
        }
    }
}
