package com.quiz.api.jersey.exception.mappers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.quiz.api.jersey.exception.CustomException;

@Provider
public class UserExceptionMapper implements ExceptionMapper<CustomException>{

	@Override
	public Response toResponse(CustomException exception) {
		
		return Response.status(exception.getStatusCode()).type(MediaType.APPLICATION_JSON).entity(exception).build();
	}

	
}
