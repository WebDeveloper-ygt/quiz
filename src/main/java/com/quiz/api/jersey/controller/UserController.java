package com.quiz.api.jersey.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.UserBean;
import com.quiz.api.jersey.security.Authenticate;
import com.quiz.api.jersey.service.impl.UserServiceImpl;
import com.quiz.api.jersey.utils.ThreadExecutor;

import org.apache.log4j.Logger;

import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;

@Path("/users")
/*@Api(value = "UserController", authorizations = {
        @Authorization(value = "JWT-tokens", scopes = {})
})*/
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserController{

	private static final Logger LOG = Logger.getLogger(UserController.class);
	private static final UserServiceImpl userServiceImpl = new UserServiceImpl();
	private static final ExecutorService executorService = ThreadExecutor.getExecutor();
	@Context
	UriInfo uriInfo;

	public UserController() {
		LOG.info("Invoked : " +this.getClass().getName());
	}

	@GET
	@Authenticate
	public void  getAllUsers(@Suspended AsyncResponse asyncResponse){

    CompletableFuture.supplyAsync(()->{
       		Response allusers = null;
		   try {
			   allusers = userServiceImpl.getAllUsers(uriInfo);
		   } catch (ExceptionOccurred | CustomException exception) {
			  LOG.error("Exception Occurred : "+ exception.getMessage());
		   }
		   return allusers;
	   }, executorService ).thenAccept(response -> asyncResponse.resume(response));

    }

	@GET
	@Path("{userId : [0-9]*}")
	public void getUser(@PathParam("userId") int userId,@Suspended AsyncResponse asyncResponse ) throws ExceptionOccurred, CustomException {
		
		CompletableFuture.supplyAsync(()->{
			Response user = null;
			try {
				user = userServiceImpl.getUser(uriInfo,userId);
			} catch (ExceptionOccurred | CustomException exception) {
				LOG.error("Exception Occurred : "+ exception.getMessage());
			}
			return user;
		},executorService).thenAccept(response -> asyncResponse.resume(response));

	}

	@POST
	public void addUser(UserBean user,@Suspended AsyncResponse asyncResponse) throws ExceptionOccurred, CustomException {
		CompletableFuture.supplyAsync(()->{
			Response addUser = null;
			try {
				addUser = userServiceImpl.addUser(uriInfo,user);
			} catch (ExceptionOccurred | CustomException exception) {
				exception.printStackTrace();
			}
			return addUser;
		},executorService).thenAccept(response -> asyncResponse.resume(response));
	}

	@PUT
	@Path("{userId}")
	public void updateUser(UserBean user, @PathParam("userId") int userId, @Suspended AsyncResponse asyncResponse) throws ExceptionOccurred, CustomException{
		CompletableFuture.supplyAsync(()->{
			Response updateUser = null;
			try {
				updateUser = userServiceImpl.updateUser(uriInfo,user,userId);
			} catch (ExceptionOccurred | CustomException exception) {
				exception.printStackTrace();
			}
			return updateUser;
		},executorService).thenAccept(response -> asyncResponse.resume(response));
	}

	@DELETE
	@Path("{userId}")
	public void deleteUser(@PathParam("userId") int userId, @Suspended AsyncResponse asyncResponse) throws ExceptionOccurred, CustomException {
		CompletableFuture.supplyAsync(()->{
			Response deleteUser = null;
			try {
				deleteUser = userServiceImpl.deleteUser(uriInfo,userId);
			} catch (ExceptionOccurred | CustomException exception) {
				exception.printStackTrace();
			}
			return deleteUser;
		},executorService).thenAccept(response -> asyncResponse.resume(response)); 
	}


	@Path("{userId}/exams")
	public UserExamController getUserExams() {
		return new UserExamController();
	}
}
