package com.web.app.service.exceptions;

import com.web.app.service.UsersService;

/**
 * This exception is thrown whenever a user signs up with a username and email,
 * which are already present in database.
 *
 * @see UsersService
 * @see com.web.app.service.impl.UsersServiceImpl
 */
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
