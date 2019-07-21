package com.quiz.api.jersey.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.dao.ExamDao;
import com.quiz.api.jersey.dao.QuestionsDao;
import com.quiz.api.jersey.dao.UserDao;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.QuestionBean;
import com.quiz.api.jersey.service.QuestionsService;
import com.quiz.api.jersey.utils.HateoasUtils;
import com.quiz.api.jersey.utils.Links;

public class QuestionsServiceImpl implements QuestionsService {

	static Logger LOG = Logger.getLogger(QuestionsServiceImpl.class);
	private static QuestionsDao questionsDao = new QuestionsDao();
	private static ExamDao examDao = new ExamDao();
	private static UserDao userDao = new UserDao();
	private static List<Links> examLinks;

	public QuestionsServiceImpl() {
		LOG.info("Invoked " + this.getClass().getName());
	}

	@Override
	public Response getAllQuestionsByExamId(UriInfo uriInfo, int examId, int userId)
			throws ExceptionOccurred, CustomException {
		Response examsByExamId = userDao.getExamsByExamId(uriInfo, userId, examId);
		if (examsByExamId.getStatus() == 200) {
			Response addQuestionsByExamId = questionsDao.getAllQuestionsByExamId(uriInfo, examId, userId);
			return addQuestionsByExamId;
		} else {
			examLinks = new ArrayList<>();
			examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
			return Response.status(Status.NOT_FOUND).entity(new CustomException("Exam Not Found", 404,
					"Exam for the ExamID " + examId + " Not Found", examLinks)).build();
		}
	}

	@Override
	public Response getQuestionsByQuestionId(UriInfo uriInfo, int examId, int questionId, int userId)
			throws ExceptionOccurred, CustomException {
		Response examsByExamId = userDao.getExamsByExamId(uriInfo, userId, examId);
		if (examsByExamId.getStatus() == 200) {
			Response questionsByQuestionId = questionsDao.getQuestionsByQuestionId(uriInfo, examId, questionId, userId);
			return questionsByQuestionId;
		} else {
			examLinks = new ArrayList<>();
			examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
			return Response.status(Status.NOT_FOUND).entity(new CustomException("Exam Not Found", 404,
					"Exam for the ExamID " + examId + " Not Found", examLinks)).build();
		}

	}

	@Override
	public Response addQuestionsByExamId(UriInfo uriInfo, int examId,int userId,QuestionBean questionBean) throws ExceptionOccurred, CustomException {
		Response examsByExamId = userDao.getExamsByExamId(uriInfo, userId, examId);
		if(examsByExamId.getStatus() == 200) {
			boolean valid=(questionBean.getOptions().getOption_A().equalsIgnoreCase("") || (questionBean.getOptions().getOption_A() == null) || questionBean.getOptions().getOption_B().equalsIgnoreCase("") || (questionBean.getOptions().getOption_B() == null) || questionBean.getOptions().getOption_Correct().equalsIgnoreCase("") || (questionBean.getOptions().getOption_Correct() == null)) ? false : true;
			if(valid) {
				Response addQuestionsByExamId = questionsDao.addQuestionsByExamId(uriInfo, examId,userId,questionBean);
				return addQuestionsByExamId;
			}else {
				return Response.status(Status.BAD_REQUEST).entity(new CustomException("Input is not valid", 400,
						"Given input is not a valid "+ questionBean, examLinks)).build();
			}
		}else {
			examLinks = new ArrayList<>();
			examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
			return Response.status(Status.NOT_FOUND).entity(new CustomException("Exam Not Found", 404,
					"Exam for the ExamID " + examId + " Not Found", examLinks)).build();
		}
	

	}

	@Override
	public Response updateQuestionsByQuestionId(UriInfo uriInfo, int examId, int questionId, int userId,
			QuestionBean questionBean) throws ExceptionOccurred, CustomException {
		Response updateQuestionsByQuestionId = questionsDao.updateQuestionsByQuestionId(uriInfo, examId, questionId,
				userId, questionBean);
		return updateQuestionsByQuestionId;
	}

	@Override
	public Response deleteQuestionsByQuestionId(UriInfo uriInfo, int examId, int questionId, int userId)
			throws ExceptionOccurred, CustomException {
		Response deleteQuestionsByQuestionId = questionsDao.deleteQuestionsByQuestionId(uriInfo, examId, questionId,
				userId);
		return deleteQuestionsByQuestionId;
	}

}
