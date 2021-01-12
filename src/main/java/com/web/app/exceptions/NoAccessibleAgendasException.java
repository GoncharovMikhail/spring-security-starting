package com.web.app.exceptions;

/**
 * This exception is thrown when a user queries another user's accessible agendas,
 * but that user has no <strong>accessible</strong> agendas.
 *
 * @see com.web.app.rest.CommonTemplatesController#search(String)
 */
public class NoAccessibleAgendasException extends Exception {

    public NoAccessibleAgendasException() {
        super();
    }

    public NoAccessibleAgendasException(String msg) {
        super(msg);
    }
}
