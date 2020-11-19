package com.web.app.service;

import com.web.app.entity.UsersEntity;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.model.UpdateAgendaByItsIdRequestDTO;
import com.web.app.service.exceptions.UserAlreadyExistsException;

/**
 * An interface, representing all necessary methods each P{@code Service} implementation should implement.
 */
public interface Service {

    /**
     * This Method saves user in database by email, username and password, which are wrapped by signUpRequest.
     *
     * @param signUpRequest the wrapper for email, username and password.
     * @throws UserAlreadyExistsException iff there is the same username/password/username and password in database.
     */
    void saveUserInDatabase(SignUpRequestDTO signUpRequest) throws UserAlreadyExistsException;

    /**
     * This Method finds all user's(like his email, roles, agendas e.t.c.) data.
     * {@link UsersEntity} wraps all this data.
     *
     * @param name the specified username.
     * @return all user's data, represented as entity.
     * @see com.web.app.security.CustomUserDetailsService#loadUserByUsername(String)
     */
    UsersEntity findByName(String name);


    /**
     * This method updates user's agenda by specified agenda's id.
     *
     * @param updateAgendaRequest wrapper for data, like day, time, note, ...
     */
    void updateAgendaById(UpdateAgendaByItsIdRequestDTO updateAgendaRequest);

    /**
     * This method updates user's agenda by specified agenda's id.
     *
     * @param agendaId agenda's id.
     */
    void deleteAgendaById(Long agendaId);
}
