package com.jleepersonal.rest_api;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.jleepersonal.model.User;

@Path("/auth")
public class AuthSvc {
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)    
	@Produces(MediaType.APPLICATION_JSON)
	public User login(User user) {		
		String pw = user.getPassword();
		String username = user.getUsername();
		
		System.out.println("Logging in for username: " + username);
		
		// TODO: Do a proper check against Mongo here.
		// Also, try return 401 or something when failure.
		if (username.equals("asd@asd.com") && pw.equals("zxc")) {
			//TODO: create JWT and return it
			User res = new User(null, "username1", "password1");
			
			return res;
		}
		
		return null;
	}
}
