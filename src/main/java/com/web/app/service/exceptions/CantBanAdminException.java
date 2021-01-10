package com.web.app.service.exceptions;

import com.web.app.service.UsersService;

/**
 * This exception is thrown whenever an admin tries to ban another admin.
 *
 * @see UsersService
 * @see com.web.app.service.impl.UsersServiceImpl
 */
public class CantBanAdminException extends Exception {

    public CantBanAdminException() {
        super();
    }

    public CantBanAdminException(String msg) {
        super(msg);
    }
}
