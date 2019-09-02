package com.quiz.api.jersey.service.impl;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.dao.UserDao;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.UserBean;

public class UserServiceImpl  {

	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
	private static final UserDao userDao= new UserDao();
	
	
	public UserServiceImpl() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	public static Response getAllUsers()throws ExceptionOccurred, CustomException{
        return userDao.getAllUsers();
	}

	public Response getUser(int userId)  throws ExceptionOccurred, CustomException{
        return userDao.getUser(userId);
	
	}

	public Response addUser(UserBean user) throws ExceptionOccurred, CustomException {
        return userDao.addUser(user);
	}

	public Response updateUser(UserBean user, int userId) throws ExceptionOccurred, CustomException{
        return userDao.updateUser(user,userId);
	}

	public Response deleteUser(int userId) throws ExceptionOccurred, CustomException {
        return userDao.deleteUser(userId);
	}

	public Response getExamsByExamAndUserId(int userId) throws ExceptionOccurred, CustomException{
        return userDao.getExamsByExamAndUserId(userId);
	}

	public Response getExamsByExamId(int userId, int examId) throws ExceptionOccurred, CustomException {
        return userDao.getExamsByExamId( userId,examId);
	}
	
}
