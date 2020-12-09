package com.web.app.service;

import com.web.app.entity.UsersEntity;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.service.exceptions.UserAlreadyExistsException;

/**
 * An interface, representing users data management operations,
 * like save new user in the database, load user by username...
 */
public interface UsersService {

    /**
     * This Method saves user in database by email, username and password, which are wrapped by signUpRequest.
     *
     * @param signUpRequest the wrapper for email, username and password.
     * @throws UserAlreadyExistsException iff there is the same username/password/username and password in database.
     */
    void saveUserInDatabase(SignUpRequestDTO signUpRequest) throws UserAlreadyExistsException;

    /**
     * This Method loads all user's(like his email, roles, agendas e.t.c.) data by username.
     * {@link UsersEntity} wraps all this data.
     *
     * @param username the specified username.
     * @return all user's data, represented as entity.
     * @see com.web.app.security.UsersDetailsService#loadUserByUsername(String)
     */
    UsersEntity loadUserByUsername(String username);
}
