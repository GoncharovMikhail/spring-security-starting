package com.web.app.service;

import com.web.app.model.SaveNewAgendaRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;

/**
 * An interface, representing agenda-management operations,
 * like save, update, delete...
 */
public interface AgendaService {

    /**
     * This method saves new agenda to the database
     *
     * @param saveNewAgendaRequest wrapper for data, like day, time, note, ...
     */
    void saveNewAgenda(SaveNewAgendaRequestDTO saveNewAgendaRequest);

    /**
     * This method updates user's agenda by specified agenda's id.
     *
     * @param updateAgendaRequest wrapper for data, like day, time, note, ...
     */
    void updateAgendaByItsId(UpdateAgendaByItsIdRequestDTO updateAgendaRequest);

    /**
     * This method deletes user's agenda by specified agenda's id.
     *
     * @param agendaId agenda's id.
     */
    void deleteAgendaById(Long agendaId);
}
