package com.jleepersonal.rest_api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.jleepersonal.model.User;

@Path("/password")
public class PasswordSvc {
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList(@Context HttpHeaders headers) throws WebApplicationException {
		Response res = null;
		
		List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
		
		System.out.println("JLEE: AUTH HEADER" + authHeaders);
		
		return res;
	}
}
