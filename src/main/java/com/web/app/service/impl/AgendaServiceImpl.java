package com.web.app.service.impl;

import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.repository.AgendaRepository;
import com.web.app.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    @Autowired
    public AgendaServiceImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }
    
    /**
     * This method updates agenda by specified id.
     *
     * @see AgendaService#updateAgendaById(UpdateAgendaByItsIdRequestDTO) for additional details.
     */
    @Override
    public void updateAgendaById(UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        agendaRepository.updateAgendaById(
                new Date(),
                updateAgendaRequest.getDay(),
                updateAgendaRequest.getTime(),
                updateAgendaRequest.isAccessible(),
                updateAgendaRequest.getNote(),
                updateAgendaRequest.getId()
        );
    }

    /**
     * This method deletes user's agenda by specified agenda's id.
     *
     * @see AgendaService#deleteAgendaById(Long)
     */
    @Override
    public void deleteAgendaById(Long agendaId) {
        agendaRepository.deleteById(agendaId);
    }
}
