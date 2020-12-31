package com.web.app.rest;

import com.web.app.model.SaveNewAgendaRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.service.AgendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * A {@link org.springframework.stereotype.Controller} to handle requests for managing agendas.
 */
@Controller
@Slf4j
public class AgendaManagementController {

    private final AgendaService agendaService;

    @Autowired
    public AgendaManagementController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * This method saves new {@code AgendaEntity}.
     *
     * @param saveNewAgendaRequest - better see it' javaDoc - {@link com.web.app.model.SignUpRequestDTO}
     * @return response status, wrapped in {@link ResponseEntity}
     */
    @PostMapping("/saveNewAgenda")
    public ResponseEntity<String> saveNewAgenda(@RequestBody SaveNewAgendaRequestDTO saveNewAgendaRequest) {
        agendaService.saveNewAgenda(saveNewAgendaRequest);

        return new ResponseEntity<>("Successfully saved your agenda!", HttpStatus.OK);
    }

    /**
     * This method updates an {@code AgendaEntity} by <strong>it's</strong> {@code id}.
     *
     * @param updateAgendaRequest - better see it' javaDoc - {@link com.web.app.model.SignUpRequestDTO}
     * @return response status, wrapped in {@link ResponseEntity}
     */
    @PostMapping("/updateAgendaByItsId")
    public ResponseEntity<String> updateAgendaById(@RequestBody UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        agendaService.updateAgendaByItsId(updateAgendaRequest);

        return new ResponseEntity<>("Successfully updated your agenda!", HttpStatus.OK);
    }

    /**
     * This method deletes an {@code AgendaEntity} by <strong>it's</strong> {@code id}.
     *
     * @param json a {@code JSON} object, in form of:
     *             {@code
     *             {
     *             "agendaId":  "agendaIdValue"
     *             }
     *             }
     *             , it will be casted to the {@code Map<String, String>}.
     * @return response status, wrapped in {@link ResponseEntity}
     */
    @PostMapping(value = "/deleteAgendaByItsId")
    public ResponseEntity<String> deleteAgendaByItsId(@RequestBody Map<String, String> json) {
        agendaService.deleteAgendaById(Long.parseLong(json.get("agendaId")));

        return new ResponseEntity<>("Successfully deleted your agenda!", HttpStatus.OK);
    }
}
