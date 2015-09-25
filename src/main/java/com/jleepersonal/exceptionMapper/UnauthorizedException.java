package com.jleepersonal.exceptionMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException {
	private static final long serialVersionUID = 6038339503418779451L;

	/**
     * Create a HTTP 401 (Unauthorized) exception.
    */
    public UnauthorizedException() {
        super(Response.status(Status.UNAUTHORIZED).build());
    }

    /**
     * Create a HTTP 401 (Unauthorized) exception.
     * @param message the String that is the entity of the 401 response.
     */
    public UnauthorizedException(String message) {
        super(Response.status(Status.UNAUTHORIZED).entity(message).type("text/plain").build());
    }
}
