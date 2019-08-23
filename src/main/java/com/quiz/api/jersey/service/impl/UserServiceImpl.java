package com.quiz.api.jersey.service.impl;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.dao.UserDao;
import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.UserBean;
import com.quiz.api.jersey.service.UserService;

public class UserServiceImpl  {

	private static Logger LOG = Logger.getLogger(UserServiceImpl.class);
	private static UserDao userDao= new UserDao();
	
	
	public UserServiceImpl() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	public static Response getAllUsers(@Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
		Response allUsers = userDao.getAllUsers(uriInfo);
		return allUsers;
	}

	public Response getUser(int userId, @Context UriInfo uriInfo)  throws ExceptionOccurred, CustomException{
		Response allUser = userDao.getUser(userId,uriInfo);
		return allUser;
	
	}

	public Response addUser(UserBean user, @Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
		Response addUser = userDao.addUser(user, uriInfo);
		return addUser;
	}

	public Response updateUser(UserBean user, int userId, @Context UriInfo uriInfo) throws ExceptionOccurred, CustomException{
		Response updateUser = userDao.updateUser(user,userId, uriInfo);
		return updateUser;
	}

	public Response deleteUser(int userId,@Context UriInfo uriInfo) throws ExceptionOccurred, CustomException {
		Response deleteUser = userDao.deleteUser(userId, uriInfo);
		return deleteUser;
	}

	public Response getExamsByExamAndUserId(@Context UriInfo uriInfo, int userId) throws ExceptionOccurred, CustomException{
		Response examByUser = userDao.getExamsByExamAndUserId(uriInfo, userId);
		return examByUser;
	}

	public Response getExamsByExamId(UriInfo uriInfo, int userId, int examId) throws ExceptionOccurred, CustomException {
		Response exam = userDao.getExamsByExamId(uriInfo, userId,examId);
		return exam;
	}
	
}
