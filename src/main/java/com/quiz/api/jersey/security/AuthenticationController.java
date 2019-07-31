package com.quiz.api.jersey.security;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.ExceptionOccurred;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("identity")
@Produces({MediaType.APPLICATION_JSON})
public class AuthenticationController {

	Logger LOG= Logger.getLogger(AuthenticationController.class);
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	public Response authenticateUser(@FormParam("user") String user, @FormParam("password") String password) throws ExceptionOccurred {
		try {
			LOG.info("Creating TOKEN for user : " + user + " and password : "+ password);
			if(user.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
				if(authenticateLoginUser(user, password)) {
				
				Token token = new Token();
				token.setToken("still we are implementing");
				token.setExpiresAt("");
				return Response.status(Status.CREATED).entity(token).build();
				}return Response.status(Status.UNAUTHORIZED).entity(new ExceptionOccurred()).build();

				
			}else {
				return Response.status(Status.UNAUTHORIZED).entity(new ExceptionOccurred()).build();		
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionOccurred();
		}
		
	}
	
	private boolean authenticateLoginUser(String user, String password) {

		if(user.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
		
			LOG.info("authenticated");
			return true;
		}else {
			return false;
		}
		
	}
}
