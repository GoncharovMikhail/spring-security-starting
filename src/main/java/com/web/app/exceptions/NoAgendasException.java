package com.web.app.exceptions;

/**
 * This exception is thrown when a user queries another user's accessible agendas,
 * but that user has no agendas <strong>at all</strong>.
 *
 * @see com.web.app.rest.CommonTemplatesController#search(String)
 */
public class NoAgendasException extends Exception {

    public NoAgendasException() {
        super();
    }

    public NoAgendasException(String msg) {
        super(msg);
    }
}
