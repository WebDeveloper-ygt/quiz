package com.quiz.api.jersey.exception.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.quiz.api.jersey.exception.ExceptionMessage;
import com.quiz.api.jersey.exception.ExceptionOccurred;

@Provider
public class ExceptionOccurredMapper implements ExceptionMapper<ExceptionOccurred> {

	@Override
	public Response toResponse(ExceptionOccurred exception) {
		ExceptionMessage message = new ExceptionMessage();
		message.setMessage("INTERNAL SERVER ERROR");
		message.setStatusCode(500);
		message.setDescription("Sorry! We are facing some technical issues");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).build();
	}

}
