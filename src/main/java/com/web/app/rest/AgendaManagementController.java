package com.web.app.rest;

import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AgendaManagementController {

    private final AgendaService agendaService;

    @Autowired
    public AgendaManagementController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping("/updateAgendaById")
    @ResponseStatus(HttpStatus.OK)
    public void updateAgendaById(@RequestBody UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        agendaService.updateAgendaById(updateAgendaRequest);
    }

    @DeleteMapping("/deleteAgendaById/{agendaId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAgendaById(@PathVariable Long agendaId) {
        agendaService.deleteAgendaById(agendaId);
    }
}
