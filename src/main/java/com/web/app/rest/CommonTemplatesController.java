package com.web.app.rest;

import com.web.app.entity.util.AgendaUtil;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.security.CustomUserDetails;
import com.web.app.service.Service;
import com.web.app.service.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class CommonTemplatesController {

    private final Service service;

    @Autowired
    public CommonTemplatesController(Service service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/success")
    public ModelAndView success(@AuthenticationPrincipal CustomUserDetails user) {
        ModelAndView modelAndView = new ModelAndView("success");
        modelAndView.addObject("username", user.getUsername());

        /* Sort agendas before adding it to modelAndView */
        modelAndView.addObject("agendas", AgendaUtil.sortAgendas(user.getAgendas()));
        return modelAndView;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(SignUpRequestDTO signUpRequest) {
        /* In this 'try'-block we'll attempt to save user in database, if it's processed fine,
         * then we redirect to login form. */
        try {
            service.saveUserInDatabase(signUpRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            return new ResponseEntity<>("UserAlreadyExistsException", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateAgendaById")
    @ResponseStatus(HttpStatus.OK)
    public void updateAgendaById(@RequestBody UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        service.updateAgendaById(updateAgendaRequest);
    }

    @DeleteMapping("/deleteAgendaById/{agendaId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAgendaById(@PathVariable Long agendaId) {
        service.deleteAgendaById(agendaId);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void exceptionHandler(RuntimeException r) {
        log.error(r.getMessage());
    }
}

