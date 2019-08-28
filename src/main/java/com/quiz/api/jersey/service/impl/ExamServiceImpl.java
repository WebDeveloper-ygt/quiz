package com.quiz.api.jersey.service.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.dao.ExamDao;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.ExamBean;
import com.quiz.api.jersey.service.ExamService;

public class ExamServiceImpl implements ExamService {

	private static final ExamDao examDao = new ExamDao();
	
	public ExamServiceImpl() {
		Logger LOG = Logger.getLogger(ExamServiceImpl.class);
		LOG.info("Invoked " +this.getClass().getName());
	}

	@Override
	public Response addExams(UriInfo uriInfo, ExamBean examBean, int userId) throws ExceptionOccurred {
        return examDao.addExams(uriInfo, examBean, userId);
		
	}

	@Override
	public Response updateExams(UriInfo uriInfo, ExamBean examBean, int userId, int examId)
			throws ExceptionOccurred, CustomException {
        return examDao.updateExams(uriInfo, examBean, userId, examId);
	}

	@Override
	public Response deleteExams(UriInfo uriInfo, int userId, int examId)
			throws ExceptionOccurred, CustomException {
        return examDao.deleteExams(uriInfo, userId, examId);
	}

}
