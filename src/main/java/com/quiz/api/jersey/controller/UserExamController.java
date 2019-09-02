package com.quiz.api.jersey.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

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

import org.apache.log4j.Logger;

import com.google.common.util.concurrent.ExecutionError;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.ExamBean;
import com.quiz.api.jersey.service.ExamService;
import com.quiz.api.jersey.service.impl.ExamServiceImpl;
import com.quiz.api.jersey.service.impl.UserServiceImpl;
import com.quiz.api.jersey.utils.ThreadExecutor;


@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserExamController{

	private static final Logger LOG = Logger.getLogger(UserController.class);
	private static final UserServiceImpl userServiceImpl = new UserServiceImpl();
	private static final ExamServiceImpl examServiceImpl = new ExamServiceImpl();
	private static final ExecutorService executorService = ThreadExecutor.getExecutor();
	@Context UriInfo uriInfo;
	UserExamController() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	@GET
	public void getExamsByExamAndUserId(@PathParam("userId") int userId, @Suspended AsyncResponse asyncResponse)
			throws ExceptionOccurred, CustomException {
		CompletableFuture.supplyAsync(()->{
			Response examId =null;
			try {
				examId=userServiceImpl.getExamsByExamAndUserId(userId);
			} catch (ExceptionOccurred | CustomException exception) {
				exception.printStackTrace();
			}
			return examId;
		},executorService).thenAccept(response -> asyncResponse.resume(response));		
	}

	@GET
	@Path("{examId}")
	public Response getExamsByExamId(@PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
        return userServiceImpl.getExamsByExamId(userId, examId);
	}

	@POST
	public Response addExams(ExamBean examBean,@PathParam("userId") int userId)
			throws ExceptionOccurred, CustomException {
        return examServiceImpl.addExams( examBean, userId);
	}

	@PUT
	@Path("{examId}")
	public Response updateExams(ExamBean examBean, @PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
        return examServiceImpl.updateExams(examBean, userId, examId);
	}

	@DELETE
	@Path("{examId}")
	public Response deleteExams(@PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
        return examServiceImpl.deleteExams(userId, examId);
	}
	
	@Path("{examId}/questions")
	public ExamQuestionsController examQuestionsController() {
		return new ExamQuestionsController();
	}
}
