package com.jleepersonal.rest_api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.InvalidJwtSignatureException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

import com.jleepersonal.exceptionMapper.ForbiddenException;
import com.jleepersonal.exceptionMapper.UnauthorizedException;

@Path("/password")
public class PasswordSvc {
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getList(@Context HttpServletRequest request) throws WebApplicationException, ServletException {
		Response resp = null;
		
		String header;
		try {
			header = this.getAuthorizationHeader(request);
			
			JwtClaims jwtClaims = getValidJWTToken(header);	
			
			JSONObject jo = new JSONObject();
			jo.put("theme", "XAYhNHhxN0A");
			
			resp = Response.status(200).entity(jo).type("application/json").build();
		} catch (IOException e) {
			System.out.println("JLOG: Cannot access secret!");
			e.printStackTrace();
			
			resp = Response.status(500).entity("Cannot find key").type("text/plain").build();
		} 
				
		return resp;
	}
	
    private JwtClaims getValidJWTToken(String token) throws IOException {
		JwtClaims jwtClaims = null;
				
		try {
			String secret = new String(Files.readAllBytes(Paths.get("secret.txt"))).trim();
			Key key = new HmacKey(secret.getBytes("UTF-8"));
			
			JwtConsumer jwtConsumer = new JwtConsumerBuilder()
			        .setRequireExpirationTime()
			        .setAllowedClockSkewInSeconds(30)
			        .setRequireSubject()
			        .setExpectedIssuer("JonathanLee")
			        .setVerificationKey(key)
			        .setRelaxVerificationKeyValidation() // relaxes key length requirement 
			        .build();
		    
	        //  Validate the JWT and process it to the Claims
	        jwtClaims = jwtConsumer.processToClaims(token);
	        System.out.println("JLOG: JWT validation succeeded! " + jwtClaims);		
	        
	        return jwtClaims;
		} catch (IOException e) {
			System.out.println("JLOG: Cannot access secret!");
			e.printStackTrace();			
			throw e;
		} catch (InvalidJwtException e) {
			System.out.println("JLOG: InvalidJwtException!");
			e.printStackTrace();
			
			if (e instanceof InvalidJwtSignatureException) {
				throw new ForbiddenException("Hacker!");
			}
			
			throw new UnauthorizedException("Unauthorized");
		}
	}
	
    private String getAuthorizationHeader(HttpServletRequest httpRequest) throws ServletException {
    	String token = null;
        final String authorizationHeader = httpRequest.getHeader("authorization");
        if (authorizationHeader == null) {
            throw new ServletException("Unauthorized: No Authorization header was found");
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2) {
            throw new ServletException("Unauthorized: Format is Authorization: Bearer [token]");
        }

        String scheme = parts[0];
        String credentials = parts[1];

        Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(scheme).matches()) {
            token = credentials;
        }
        return token;
    }
}
