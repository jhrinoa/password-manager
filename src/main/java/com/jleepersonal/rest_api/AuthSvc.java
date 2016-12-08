package com.jleepersonal.rest_api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import com.jleepersonal.exceptionMapper.UnauthorizedException;
import com.jleepersonal.model.User;

@Path("/auth")
public class AuthSvc {
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)    
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) throws WebApplicationException {		
		String pw = user.getPassword();
		String username = user.getUsername();
		
		System.out.println("Logging in for username: " + username);
		
		Response resp = null;
		
		// TODO: Do a proper check against Mongo here.
		if (pw.equals("zxc")) {
			System.out.println("Successful Login. Creating token...");			

			String jwt = null;
			
			try {
				jwt = createJWT(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				resp = Response.status(500).entity("Cannot create key").type("text/plain").build();
			}
			
			resp = Response.status(200).entity(jwt).type("text/plain").build();
			
			return resp;
		} else {
			System.out.println("Login failed... Throw UnauthorizedException");
			throw new UnauthorizedException("Unauthorized");
		}
	}
	
	private String createJWT(String userName) throws JoseException, NoSuchAlgorithmException, IOException {
		// More information on
		// https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
		// http://stackoverflow.com/questions/32006323/how-to-make-hmac-sha256-key-from-secret-string-to-use-it-with-jwt-in-jose4j
		// http://stackoverflow.com/questions/21978658/invalidating-json-web-tokens
		String jwt = null;

	    // Create the Claims, which will be the content of the JWT
	    JwtClaims claims = new JwtClaims();
	    claims.setIssuer("JonathanLee");  // who creates the token and signs it	 
	    claims.setExpirationTimeMinutesInTheFuture(5); // time when the token will expire (5 minutes from now)
	    claims.setGeneratedJwtId(); // a unique identifier for the token
	    claims.setIssuedAtToNow();  // when the token was issued/created (now)
	    claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
	    claims.setSubject("authorized user"); // the subject/principal is whom the token is about
	    
	    // TODO: If we need customized info...
//	    List<String> groups = null;	    	   
//	    claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array

	    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
	    // In this example it is a JWS so we create a JsonWebSignature object.
	    String secret = new String(Files.readAllBytes(Paths.get("secret.txt"))).trim();
	    
	    Key key = new HmacKey(secret.getBytes("UTF-8"));

	    JsonWebSignature jws = new JsonWebSignature();
	    jws.setPayload(claims.toJson());
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
	    jws.setKey(key);
	    jws.setDoKeyValidation(false); // relaxes the key length requirement

	    jwt = jws.getCompactSerialization();	    


	    System.out.println("JLOG: Generated JWT = [" + jwt + "]");
		
		return jwt;
	}
}
