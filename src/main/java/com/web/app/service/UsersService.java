package com.web.app.service;

import com.web.app.entity.UsersEntity;
import com.web.app.model.SignUpRequestDTO;
import com.web.app.service.exceptions.UserAlreadyExistsException;
import org.springframework.data.repository.query.Param;

/**
 * An interface, representing users data management operations,
 * like
 * <ul>
 *     <li>
 *         Saving new user in the database,
 *     </li>
 *     <li>
 *         Loading user by username,
 *     </li>
 *     <li>
 *         ...
 *     </li>
 * </ul>
 */
public interface UsersService {

    /**
     * This method saves user in database by email, username and password, which are wrapped in signUpRequest.
     *
     * @param signUpRequest the wrapper for email, username and password.
     * @throws UserAlreadyExistsException iff there is the same email/username in database.
     */
    void saveUserInDatabase(SignUpRequestDTO signUpRequest) throws UserAlreadyExistsException;

    /**
     * This method loads all user's(like his email, roles, agendas e.t.c.) data by username.
     * {@link UsersEntity} wraps all this data.
     *
     * @param username the specified username.
     * @return all user's data, represented as {@code entity}.
     * @see com.web.app.repository.UsersRepository#findByUsername(String)
     * @see com.web.app.security.UsersDetailsService#loadUserByUsername(String)
     */
    UsersEntity loadUserByUsername(String username);

    /**
     * @see com.web.app.repository.UsersRepository#enableOrDisableUser(boolean, String)
     */
    void enableOrDisableUser(boolean toEnable, String username);
}
