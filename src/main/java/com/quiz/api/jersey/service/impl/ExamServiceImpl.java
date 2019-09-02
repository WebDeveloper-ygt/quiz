package com.quiz.api.jersey.service.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.dao.ExamDao;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.ExamBean;
import com.quiz.api.jersey.service.ExamService;

public class ExamServiceImpl {

	private static final ExamDao examDao = new ExamDao();
	
	public ExamServiceImpl() {
		Logger LOG = Logger.getLogger(ExamServiceImpl.class);
		LOG.info("Invoked " +this.getClass().getName());
	}


	public Response addExams(ExamBean examBean, int userId) throws ExceptionOccurred {
        return examDao.addExams(examBean, userId);
		
	}

	public Response updateExams(ExamBean examBean, int userId, int examId)
			throws ExceptionOccurred, CustomException {
        return examDao.updateExams( examBean, userId, examId);
	}

	public Response deleteExams(int userId, int examId)
			throws ExceptionOccurred, CustomException {
        return examDao.deleteExams(userId, examId);
	}

}
