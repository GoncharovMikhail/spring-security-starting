package com.web.app.exceptions;

/**
 * This exception is thrown whenever an admin tries to ban another admin.
 *
 * @see com.web.app.service.AdminService#banUserByUsername(String)
 * @see com.web.app.service.impl.AdminServiceImpl#banUserByUsername(String)
 */
public class CantBanAdminException extends Exception {

    public CantBanAdminException() {
        super();
    }

    public CantBanAdminException(String msg) {
        super(msg);
    }
}
