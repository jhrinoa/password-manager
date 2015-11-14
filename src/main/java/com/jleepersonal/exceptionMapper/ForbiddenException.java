package com.jleepersonal.exceptionMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ForbiddenException extends WebApplicationException {	
	/**
	 * Generated
	 */
	private static final long serialVersionUID = -7321847691809048589L;

	/**
     * Create a HTTP 401 (Unauthorized) exception.
    */
    public ForbiddenException() {
        super(Response.status(Status.FORBIDDEN).build());
    }

    /**
     * Create a HTTP 401 (Unauthorized) exception.
     * @param message the String that is the entity of the 401 response.
     */
    public ForbiddenException(String message) {
        super(Response.status(Status.FORBIDDEN).entity(message).type("text/plain").build());
    }
}
