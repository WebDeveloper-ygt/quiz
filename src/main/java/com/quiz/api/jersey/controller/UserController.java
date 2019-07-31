package com.quiz.api.jersey.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.model.UserBean;
import com.quiz.api.jersey.security.Authenticate;
import com.quiz.api.jersey.service.UserService;
import com.quiz.api.jersey.service.impl.UserServiceImpl;

@Path("/users")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserController implements UserService {

	static Logger LOG = Logger.getLogger(UserController.class);
	private static UserServiceImpl userServiceImpl = new UserServiceImpl();
	
	public UserController() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	@GET
	@Authenticate
	public Response getAllUsers(@Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
		Response allUsers = userServiceImpl.getAllUsers(uriInfo);
		return allUsers;

	}

	@GET
	@Path("{userId}")
	public Response getUser(@PathParam("userId") int userId,@Context UriInfo uriInfo ) throws ExceptionOccurred, CustomException {
		Response user = userServiceImpl.getUser(userId,uriInfo);
		return user;
	}

	@POST
	public Response addUser(UserBean user, @Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
		Response addUser = userServiceImpl.addUser(user,uriInfo);
		return addUser;
	}

	@PUT
	@Path("{userId}")
	public Response updateUser(UserBean user, @PathParam("userId") int userId,@Context UriInfo uriInfo) throws ExceptionOccurred, CustomException{
		Response updateUser = userServiceImpl.updateUser(user,userId,uriInfo);
		return updateUser;
	}

	@DELETE
	@Path("{userId}")
	public Response deleteUser(@PathParam("userId") int userId,@Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
		Response deleteUser = userServiceImpl.deleteUser(userId,uriInfo);
		return deleteUser;
	}


	@Path("{userId}/exams")
	public UserExamController getUserExams() {
		return new UserExamController();
	}
}
