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
import com.quiz.api.jersey.model.QuestionBean;
import com.quiz.api.jersey.service.QuestionsService;
import com.quiz.api.jersey.service.impl.QuestionsServiceImpl;

@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ExamQuestionsController {

	private static final Logger LOG = Logger.getLogger(ExamQuestionsController.class);
	private static final QuestionsServiceImpl quesrtionsImpl = new QuestionsServiceImpl();
	@Context
	UriInfo uriInfo;
	public ExamQuestionsController() {
		LOG.info("Invoked " + this.getClass().getName());
	}

	@GET
	public Response getAllQuestionsByExamId(@PathParam("examId") int examId,
			@PathParam("userId") int userId) throws ExceptionOccurred, CustomException {
		// TODO Auto-generated method stub
		return quesrtionsImpl.getAllQuestionsByExamId(examId,userId);
	}

	@GET
	@Path("{questionId}")
	public Response getQuestionsByQuestionId(@PathParam("examId") int examId,
			@PathParam("questionId") int questionId,@PathParam("userId") int userId) throws ExceptionOccurred, CustomException {
		return quesrtionsImpl.getQuestionsByQuestionId(examId, questionId,userId);
	}

	@POST
	public Response addQuestionsByExamId(@PathParam("examId") int examId,@PathParam("userId") int userId, QuestionBean questionBean)
			throws ExceptionOccurred, CustomException {
		// TODO Auto-generated method stub
		return quesrtionsImpl.addQuestionsByExamId(examId,userId,questionBean);
	}

	@PUT
	@Path("{questionId}")
	public Response updateQuestionsByQuestionId(@PathParam("examId") int examId,
			@PathParam("questionId") int questionId,@PathParam("userId") int userId,QuestionBean questionBean) throws ExceptionOccurred, CustomException {
		// TODO Auto-generated method stub
		return quesrtionsImpl.updateQuestionsByQuestionId(examId, questionId,userId,questionBean);

	}

	@DELETE
	@Path("{questionId}")
	public Response deleteQuestionsByQuestionId(@PathParam("examId") int examId,
			@PathParam("questionId") int questionId,@PathParam("userId") int userId) throws ExceptionOccurred, CustomException {
		return quesrtionsImpl.deleteQuestionsByQuestionId(examId, questionId,userId);
	}

}
