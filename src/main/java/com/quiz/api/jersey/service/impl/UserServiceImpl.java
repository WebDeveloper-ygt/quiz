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

	public static Response getAllUsers(UriInfo uriInfo)throws ExceptionOccurred, CustomException{
        return userDao.getAllUsers(uriInfo);
	}

	public Response getUser(UriInfo uriInfo,int userId)  throws ExceptionOccurred, CustomException{
        return userDao.getUser(uriInfo,userId);
	
	}

	public Response addUser(UriInfo uriInfo,UserBean user) throws ExceptionOccurred, CustomException {
        return userDao.addUser(uriInfo,user);
	}

	public Response updateUser(UriInfo uriInfo,UserBean user, int userId) throws ExceptionOccurred, CustomException{
        return userDao.updateUser(uriInfo,user,userId);
	}

	public Response deleteUser(UriInfo uriInfo,int userId) throws ExceptionOccurred, CustomException {
        return userDao.deleteUser(uriInfo,userId);
	}

	public Response getExamsByExamAndUserId(UriInfo uriInfo,int userId) throws ExceptionOccurred, CustomException{
        return userDao.getExamsByExamAndUserId(uriInfo,userId);
	}

	public Response getExamsByExamId(UriInfo uriInfo,int userId, int examId) throws ExceptionOccurred, CustomException {
        return userDao.getExamsByExamId( uriInfo,userId,examId);
	}
	
}
