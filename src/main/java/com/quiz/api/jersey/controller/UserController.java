package com.quiz.api.jersey.controller;

import javax.inject.Inject;
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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.model.UserBean;
import com.quiz.api.jersey.security.Authenticate;
import com.quiz.api.jersey.service.UserService;
import com.quiz.api.jersey.service.impl.UserServiceImpl;
import org.glassfish.jersey.server.ManagedAsync;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.concurrent.*;

@Path("/users")
@Api(value = "UserController", authorizations = {
        @Authorization(value = "JWT-tokens", scopes = {})
})
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserController/* implements UserService*/ {

	private static Logger LOG = Logger.getLogger(UserController.class);
	private static UserServiceImpl userServiceImpl = new UserServiceImpl();
	private  ExecutorService executorService = Executors.newSingleThreadExecutor();
	public UserController() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	@GET
	@Authenticate
    //@ManagedAsync
	public void /*CompletionStage*/ /*Response*/ getAllUsers(@Context UriInfo uriInfo, @Suspended AsyncResponse asyncResponse) throws ExceptionOccurred, CustomException {

	    /*
	    CompletableFuture future = new CompletableFuture<>();
	    new Thread( () ->{
            Response allUsers = null;
	       try{
	          allUsers= userServiceImpl.getAllUsers(uriInfo);
           }catch (ExceptionOccurred | CustomException e){
	           e.printStackTrace();
           }
	       future.complete(allUsers);
        }).start();
	    return  future;
	    */

       /* Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Response allUsers = userServiceImpl.getAllUsers(uriInfo);
                    asyncResponse.resume(allUsers);
                } catch (ExceptionOccurred | CustomException e) {
                    e.printStackTrace();
                }
            }
        };
        System.out.println(Thread.currentThread().getName());
        executorService.submit(runnable);
        */

        /*
        Callable<Response> callable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                Response allUsers = userServiceImpl.getAllUsers(uriInfo);
                return allUsers;
            }
        };

        Future<Response> submit = executorService.submit(callable);
        try {
            asyncResponse.resume(submit.get());
        }catch ( ExecutionException |InterruptedException e ){
            e.printStackTrace();
        }

        executorService.shutdown();
        */

       /*
        Response allUsers = userServiceImpl.getAllUsers(uriInfo);
        return allUsers;
        */

       /*
       new Thread(()->{
           Response allUsers =null;
           try{
               allUsers = userServiceImpl.getAllUsers(uriInfo);
           }catch (ExceptionOccurred | CustomException e){
               e.printStackTrace();
           }
           asyncResponse.resume(allUsers);
       }).start();
       */

       /*
       executorService.execute(()->{
            try {
                asyncResponse.setTimeout(1000, TimeUnit.MILLISECONDS);
                asyncResponse.setTimeoutHandler(timeout->{
                    timeout.resume(Response.status(Response.Status.REQUEST_TIMEOUT).entity("Request timeout happened").build());
                });
                Response allUsers = userServiceImpl.getAllUsers(uriInfo);
                asyncResponse.resume(allUsers);
            }catch (ExceptionOccurred | CustomException e){
                e.printStackTrace();
            }
        });
        */

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response allUsers = userServiceImpl.getAllUsers(uriInfo);
                    asyncResponse.resume(allUsers);
                }catch (ExceptionOccurred | CustomException e){
                    e.printStackTrace();
                }
            }
        }).start();
        */

       CompletableFuture.supplyAsync(()->{
       		Response allusers = null;
		   try {
			   allusers = userServiceImpl.getAllUsers(uriInfo);
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
			allusers=userServiceImpl.getAllUsers(uriInfo);
		} catch (ExceptionOccurred | CustomException exception) {
			exception.printStackTrace();
		}
		return allusers;
	}
	@GET
	@Path("{userId : [0-9]*}")
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
