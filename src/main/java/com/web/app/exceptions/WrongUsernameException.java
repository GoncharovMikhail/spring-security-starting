package com.web.app.exceptions;

/**
 * WrongUsernameException is used to indicate that there is no user in database, having specified username.
 * Differs from {@link org.springframework.security.core.userdetails.UsernameNotFoundException} by that
 * {@link org.springframework.security.core.userdetails.UsernameNotFoundException} is thrown when user
 * tries to <strong>authenticate</strong> with a non-existing username, meanwhile this exception
 * is supposed to be thrown when admin alters user's data by his{her} username.
 *
 * @see com.web.app.service.AdminService
 * @see com.web.app.service.impl.AdminServiceImpl
 * @see com.web.app.rest.AdminController
 * @see com.web.app.rest.CommonTemplatesController#search(String)
 */
public class WrongUsernameException extends Exception {

    public WrongUsernameException() {
        super();
    }

    public WrongUsernameException(String msg) {
        super(msg);
    }
}
