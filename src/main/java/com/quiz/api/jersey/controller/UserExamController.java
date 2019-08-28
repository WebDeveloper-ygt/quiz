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

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.ExamBean;
import com.quiz.api.jersey.service.ExamService;
import com.quiz.api.jersey.service.impl.ExamServiceImpl;
import com.quiz.api.jersey.service.impl.UserServiceImpl;


@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserExamController implements ExamService {

	private static final Logger LOG = Logger.getLogger(UserController.class);
	private static final UserServiceImpl userServiceImpl = new UserServiceImpl();
	private static final ExamServiceImpl examServiceImpl = new ExamServiceImpl();
	
	UserExamController() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	@GET
	public Response getExamsByExamAndUserId(@Context UriInfo uriInfo, @PathParam("userId") int userId)
			throws ExceptionOccurred, CustomException {
        return userServiceImpl.getExamsByExamAndUserId(uriInfo, userId);
	}

	@GET
	@Path("{examId}")
	public Response getExamsByExamId(@Context UriInfo uriInfo, @PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
        return userServiceImpl.getExamsByExamId(uriInfo, userId, examId);
	}

	@POST
	public Response addExams(@Context UriInfo uriInfo, ExamBean examBean,@PathParam("userId") int userId)
			throws ExceptionOccurred, CustomException {
        return examServiceImpl.addExams(uriInfo, examBean, userId);
	}

	@PUT
	@Path("{examId}")
	public Response updateExams(@Context UriInfo uriInfo, ExamBean examBean, @PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
        return examServiceImpl.updateExams(uriInfo, examBean, userId, examId);
	}

	@DELETE
	@Path("{examId}")
	public Response deleteExams(UriInfo uriInfo, @PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
        return examServiceImpl.deleteExams(uriInfo, userId, examId);
	}
	
	@Path("{examId}/questions")
	public ExamQuestionsController examQuestionsController() {
		return new ExamQuestionsController();
	}
}
