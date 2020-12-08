package com.web.app.service.impl;

import com.web.app.entity.AgendaEntity;
import com.web.app.entity.UsersEntity;
import com.web.app.model.SaveAgendaRequestDTO;
import com.web.app.model.SaveNewAgendaRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.repository.AgendaRepository;
import com.web.app.service.AgendaService;
import com.web.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    private final UsersService usersService;

    @Autowired
    public AgendaServiceImpl(AgendaRepository agendaRepository, UsersService usersService) {
        this.agendaRepository = agendaRepository;
        this.usersService = usersService;
    }

    /**
     * This method saves new agenda to the database
     *
     * @see AgendaService#saveNewAgenda(SaveNewAgendaRequestDTO) for additional details.
     */
    @Override
    public void saveNewAgenda(SaveNewAgendaRequestDTO saveNewAgendaRequest) {
        AgendaEntity agendaToSave = new AgendaEntity();

        UsersEntity usersid = usersService.loadUserByUsername(saveNewAgendaRequest.getUsername());

        agendaToSave.setCreated(new Date());
        agendaToSave.setUpdated(new Date());
        agendaToSave.setUsersid(usersid);
        agendaToSave.setDay(saveNewAgendaRequest.getDay());
        agendaToSave.setTime(saveNewAgendaRequest.getTime());
        agendaToSave.setNote(saveNewAgendaRequest.getNote());

        agendaRepository.save(agendaToSave);
    }
    
    /**
     * This method updates agenda by specified id.
     *
     * @see AgendaService#updateAgendaByItsId(UpdateAgendaByItsIdRequestDTO) for additional details.
     */
    @Override
    public void updateAgendaByItsId(UpdateAgendaByItsIdRequestDTO updateAgendaRequest) {
        agendaRepository.updateAgendaByItsId(
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
