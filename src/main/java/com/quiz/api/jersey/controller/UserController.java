package com.quiz.api.jersey.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.model.UserBean;
import com.quiz.api.jersey.security.Authenticate;
import com.quiz.api.jersey.service.impl.UserServiceImpl;

import java.util.concurrent.*;

@Path("/users")
@Api(value = "UserController", authorizations = {
        @Authorization(value = "JWT-tokens", scopes = {})
})
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserController{

	private static final Logger LOG = Logger.getLogger(UserController.class);
	private static final UserServiceImpl userServiceImpl = new UserServiceImpl();
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	public UserController() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	@GET
	@Authenticate
    //@ManagedAsync
	public void  getAllUsers(@Context UriInfo uriInfo, @Suspended AsyncResponse asyncResponse) {

    CompletableFuture.supplyAsync(()->{
       		Response allusers = null;
		   try {
			   allusers = UserServiceImpl.getAllUsers(uriInfo);
		   } catch (ExceptionOccurred | CustomException exception) {
			   exception.printStackTrace();
		   }
		   return allusers;
	   }, executorService ).thenAccept(response -> asyncResponse.resume(response));

    }

    public static Response getusers(){
		Response allusers = null;
		try {
			UriInfo uriInfo = null;
			allusers= UserServiceImpl.getAllUsers(uriInfo);
		} catch (ExceptionOccurred | CustomException exception) {
			exception.printStackTrace();
		}
		return allusers;
	}
	@GET
	@Path("{userId : [0-9]*}")
	public Response getUser(@PathParam("userId") int userId,@Context UriInfo uriInfo ) throws ExceptionOccurred, CustomException {
        return userServiceImpl.getUser(userId,uriInfo);
	}

	@POST
	public Response addUser(UserBean user, @Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
        return userServiceImpl.addUser(user,uriInfo);
	}

	@PUT
	@Path("{userId}")
	public Response updateUser(UserBean user, @PathParam("userId") int userId,@Context UriInfo uriInfo) throws ExceptionOccurred, CustomException{
        return userServiceImpl.updateUser(user,userId,uriInfo);
	}

	@DELETE
	@Path("{userId}")
	public Response deleteUser(@PathParam("userId") int userId,@Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
        return userServiceImpl.deleteUser(userId,uriInfo);
	}


	@Path("{userId}/exams")
	public UserExamController getUserExams() {
		return new UserExamController();
	}
}
