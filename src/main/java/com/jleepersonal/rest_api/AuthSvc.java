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
		
		// TODO: Do a proper check against Mongo here.
		// Also, try return 401 or something when failure.
		if (username.equals("asd@asd.com") && pw.equals("zxc")) {
			System.out.println("Successful Login. Creating token...");			

			String jleeJWT = null;
			
			try {
				jleeJWT = createJWT(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Response resp = Response.status(200).entity(jleeJWT).type("text/plain").build();
			
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
		String jwt = null;

	    // Create the Claims, which will be the content of the JWT
	    JwtClaims claims = new JwtClaims();
	    claims.setIssuer("Jonathan Lee");  // who creates the token and signs it
	    claims.setAudience(userName); // to whom the token is intended to be sent	 
	    claims.setExpirationTimeMinutesInTheFuture(5); // time when the token will expire (10 minutes from now)
	    claims.setGeneratedJwtId(); // a unique identifier for the token
	    claims.setIssuedAtToNow();  // when the token was issued/created (now)
	    claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
	    claims.setSubject("accessToken"); // the subject/principal is whom the token is about
	    claims.setClaim("email","mail@example.com"); // additional claims/attributes about the subject can be added
	    List<String> groups = Arrays.asList("group-one", "other-group", "group-three");
	    claims.setStringListClaim("groups", groups); // multi-valued claims work too and will end up as a JSON array

	    // A JWT is a JWS and/or a JWE with JSON claims as the payload.
	    // In this example it is a JWS so we create a JsonWebSignature object.
	    // TODO: Change content of secret.txt to  
	    String secret = new String(Files.readAllBytes(Paths.get("secret.txt"))).trim();
	    
	    Key key = new HmacKey(secret.getBytes("UTF-8"));

	    JsonWebSignature jws = new JsonWebSignature();
	    jws.setPayload(claims.toJson());
	    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
	    jws.setKey(key);
	    jws.setDoKeyValidation(false); // relaxes the key length requirement

	    jwt = jws.getCompactSerialization();	    

	    // Now you can do something with the JWT. Like send it to some other party
	    // over the clouds and through the interwebs.
	    System.out.println("JWT: " + jwt);
		
		return jwt;
	}
}
