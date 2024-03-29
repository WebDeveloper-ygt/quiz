package com.quiz.api.jersey.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.QuestionBean;

public interface QuestionsService {

	Response getAllQuestionsByExamId(UriInfo uriInfo, int examId, int userId) throws ExceptionOccurred, CustomException;
	Response getQuestionsByQuestionId(UriInfo uriInfo, int examId, int questionId, int userId) throws ExceptionOccurred, CustomException;
	Response addQuestionsByExamId(UriInfo uriInfo, int examId, int userId, QuestionBean questionBean) throws ExceptionOccurred, CustomException;
	Response updateQuestionsByQuestionId(UriInfo uriInfo, int examId, int questionId, int userId, QuestionBean questionBean) throws ExceptionOccurred, CustomException;
	Response deleteQuestionsByQuestionId(UriInfo uriInfo, int examId, int questionId, int userId) throws ExceptionOccurred, CustomException;



}
