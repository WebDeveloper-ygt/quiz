package com.quiz.api.jersey.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.ExamBean;

public interface ExamService {

	//public Response getExamsByExamAndUserId(UriInfo uriInfo, int userId) throws ExceptionOccurred, CustomException;
	//public Response getExamsByExamId(UriInfo uriInfo,int userId,int examId) throws ExceptionOccurred, CustomException;
    Response addExams(ExamBean examBean, int userId) throws ExceptionOccurred, CustomException;
	Response updateExams(ExamBean examBean, int userId, int examId) throws ExceptionOccurred, CustomException;
	Response deleteExams(int userId, int examId) throws ExceptionOccurred, CustomException;
}
