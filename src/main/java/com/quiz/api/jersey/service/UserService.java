package com.quiz.api.jersey.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.UserBean;

public interface UserService{

	Response getAllUsers(UriInfo uriInfo) throws ExceptionOccurred;
	Response getUser(int userId, UriInfo uriInfo) throws ExceptionOccurred;
	Response addUser(UserBean user, UriInfo uriInfo) throws ExceptionOccurred, CustomException;
	Response updateUser(UserBean user, int userId, UriInfo uriInfo)throws ExceptionOccurred;
	Response deleteUser(int userId, UriInfo uriInfo) throws ExceptionOccurred;
}
