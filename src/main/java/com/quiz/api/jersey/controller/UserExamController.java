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


@Path("/")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class UserExamController implements ExamService {

	static Logger LOG = Logger.getLogger(UserController.class);
	private static UserServiceImpl userServiceImpl = new UserServiceImpl();
	private static ExamServiceImpl examServiceImpl = new ExamServiceImpl();
	
	public UserExamController() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	@GET
	public Response getExamsByExamAndUserId(@Context UriInfo uriInfo, @PathParam("userId") int userId)
			throws ExceptionOccurred, CustomException {
		Response examsByExamAndUserId = userServiceImpl.getExamsByExamAndUserId(uriInfo, userId);
		return examsByExamAndUserId;
	}

	@GET
	@Path("{examId}")
	public Response getExamsByExamId(@Context UriInfo uriInfo, @PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
		Response examsByExamAndUserId = userServiceImpl.getExamsByExamId(uriInfo, userId, examId);
		return examsByExamAndUserId;
	}

	@POST
	public Response addExams(@Context UriInfo uriInfo, ExamBean examBean,@PathParam("userId") int userId)
			throws ExceptionOccurred, CustomException {
		Response addExams = examServiceImpl.addExams(uriInfo, examBean, userId);
		return addExams;
	}

	@PUT
	@Path("{examId}")
	public Response updateExams(@Context UriInfo uriInfo, ExamBean examBean, @PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
		Response updateExams = examServiceImpl.updateExams(uriInfo, examBean, userId, examId);
		return updateExams;
	}

	@DELETE
	@Path("{examId}")
	public Response deleteExams(UriInfo uriInfo, @PathParam("userId") int userId,
			@PathParam("examId") int examId) throws ExceptionOccurred, CustomException {
		Response deleteExams = examServiceImpl.deleteExams(uriInfo, userId, examId);
		return deleteExams;
	}
	
	@Path("{examId}/questions")
	public ExamQuestionsController examQuestionsController() {
		return new ExamQuestionsController();
	}
}
