package com.jleepersonal.rest_api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
public class AuthSvc {
	@GET
	@Path("/login")
//	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
//	@Produces(MediaType.APPLICATION_JSON)
	public String login() {
		System.out.println("I AM CALLED!");
		return "jlee!";
	}
}
