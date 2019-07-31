package com.quiz.api.jersey.security;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.utils.Links;

import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

@Provider
@Authenticate
@Priority(Priorities.AUTHENTICATION)
public class AuthRequestFilter implements ContainerRequestFilter {
   
	private static Logger LOG = Logger.getLogger(AuthRequestFilter.class);
	private Links link;
	private CustomException exception;
	private KeyGenerator keyGenerator;
	@Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

    	String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    	LOG.info("Authorization header value : " + authHeader );
    	
    	
    	if(authHeader == null || ! authHeader.startsWith("Bearer ")) {
    		LOG.error("Authentication failed :: Header Value is : " + authHeader );
    		 List<Links> defLinks = getCustomUnauthorization(requestContext);
    		 exception=new CustomException("Authentication failed", 401, "Please provide valide Authentication Key", defLinks);
    		throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).entity(exception).build());
    	}else {
    		try {
            String token = authHeader.substring("Bearer".length()).trim();
            Key key = keyGenerator.generateKey();
    		Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    		LOG.info("valid token : " + token);
    		}catch (Exception e) {
    			List<Links> defLinks = getCustomUnauthorization(requestContext);
       		 exception=new CustomException("Authentication failed", 401, "Please provide valide Authentication Key", defLinks);
    			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(exception).build());
    		}
    	}
    }
	private List<Links> getCustomUnauthorization(ContainerRequestContext requestContext) {

		List<Links> links = new ArrayList<>();
		link = new Links();
		link.setLink("https://"+requestContext.getHeaderString(HttpHeaders.HOST)+"/QuizApi/api/identity");
		link.setRef("getAuthenticationKey");
		links.add(link);
		
		return links;
	}
}
