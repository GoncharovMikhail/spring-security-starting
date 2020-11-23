package com.web.app.service;

import com.web.app.model.UpdateAgendaByItsIdRequestDTO;

public interface AgendaService {

    /**
     * This method updates user's agenda by specified agenda's id.
     *
     * @param updateAgendaRequest wrapper for data, like day, time, note, ...
     */
    void updateAgendaById(UpdateAgendaByItsIdRequestDTO updateAgendaRequest);

    /**
     * This method deletes user's agenda by specified agenda's id.
     *
     * @param agendaId agenda's id.
     */
    void deleteAgendaById(Long agendaId);
}
