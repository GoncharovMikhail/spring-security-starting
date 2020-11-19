package com.web.app.service.exceptions;

/**
 * This exception is thrown whenever a user signs up with a username and email,
 * which are already present in database.
 *
 * @see com.web.app.service.Service
 * @see com.web.app.service.impl.ServiceImpl
 */
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }
}
