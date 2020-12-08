package com.web.app.rest;

import com.web.app.model.SaveNewAgendaRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.service.AgendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Slf4j
public class AgendaManagementController {

    private final AgendaService agendaService;

    @Autowired
    public AgendaManagementController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping("/saveNewAgenda")
    public ResponseEntity<String> saveNewAgenda(@RequestBody SaveNewAgendaRequestDTO saveNewAgendaRequest) {
        agendaService.saveNewAgenda(saveNewAgendaRequest);

        return new ResponseEntity<>("Successfully saved your agenda!", HttpStatus.OK);
    }

    @PostMapping("/updateAgendaByItsId")
    public ResponseEntity<String> updateAgendaById(@RequestBody UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        agendaService.updateAgendaByItsId(updateAgendaRequest);

        return new ResponseEntity<>("Successfully updated your agenda!", HttpStatus.OK);
    }

    @PostMapping(value = "/deleteAgendaByItsId")
    public ResponseEntity<String> deleteAgendaByItsId(@RequestBody Map<String, String> json) {
        agendaService.deleteAgendaById(Long.parseLong(json.get("agendaId")));

        return new ResponseEntity<>("Successfully deleted your agenda!", HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException r) {
        log.error("IN " + this.getClass() + " a RuntimeException occurred: " + r);
        return new ResponseEntity<>(
                "Server couldn't process the request",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
