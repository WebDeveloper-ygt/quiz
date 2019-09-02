package com.quiz.api.jersey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.ExamBean;
import com.quiz.api.jersey.model.UserBean;
import com.quiz.api.jersey.service.UserService;
import com.quiz.api.jersey.utils.ApiUtils;
import com.quiz.api.jersey.utils.ConstantUtils;
import com.quiz.api.jersey.utils.HateoasUtils;
import com.quiz.api.jersey.utils.Links;

public class UserDao {

	private static final Logger LOG = Logger.getLogger(UserDao.class);
	private static List<UserBean> userList;
	private static List<ExamBean> examList;
	private static Connection dbConnection;
	private static List<Links> exceptionLink;
	private static String relMessage ;
	public UserDao() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	public Response getAllUsers() throws ExceptionOccurred {

		return getUserDetailsInCommon(ConstantUtils.USERS, 0);
	}

	public Response getUser(int userId) throws ExceptionOccurred {
		return getUserDetailsInCommon((ConstantUtils.USERS_ID + userId), userId);
	}

	public Response addUser(UserBean user) throws ExceptionOccurred, CustomException {
		Response userDetailsInCommon = getUserDetailsInCommon(
				(ConstantUtils.USER_EMAIL + "'" + user.getEmailId() + "'"), 0);
		LOG.info("Status of the user already present is : " + userDetailsInCommon.getStatus());
		if (userDetailsInCommon.getStatus() != 404) {
			LOG.error("User Already Present :: "+ user.getEmailId());
			exceptionLink = new ArrayList<>();
			exceptionLink.add(HateoasUtils.getSelfDetails());
			throw new CustomException("User Already Present", 400,
					"User alreay present with Emailid " + user.getEmailId() + "", exceptionLink);
		} else {
			try {
				String addUser = "INSERT INTO `quizapi`.`quiz_users`(`userName`,`firstName`,`lastName`,`emailId`,`phoneNumber`,`instituteName`,`password`)VALUES (?,?,?,?,?,?,?)";
				PreparedStatement pst = ApiUtils.getDbConnection().prepareStatement(addUser);
				pst.setString(1, user.getUserName());
				pst.setString(2, user.getFirstName());
				pst.setString(3, user.getLastName());
				pst.setString(4, user.getEmailId());
				pst.setString(5, user.getPhoneNumber());
				pst.setString(6, user.getInstituteName());
				pst.setString(7, Base64.getEncoder().encodeToString(user.getPassword().getBytes()));

				boolean execute = pst.execute();
				if (!execute) {
					return getUserDetailsInCommon(
							(ConstantUtils.USER_EMAIL + "'" + user.getEmailId() + "'"), 0);
				} else {
					return Response.status(Status.BAD_REQUEST).build();
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
				throw new ExceptionOccurred();
			}
		}
	}


	public Response updateUser(UserBean user, int userId)
			throws ExceptionOccurred {
		Response userDetailsInCommon = getUserDetailsInCommon((ConstantUtils.USERS_ID + userId), userId);
		if(userDetailsInCommon.getStatus() != 200) {
			LOG.error("User Not Found :: " + userId);
			exceptionLink = new ArrayList<>();
			exceptionLink.add(HateoasUtils.getSelfDetails());
			return Response.status(Status.NOT_FOUND).entity(new CustomException("User not Found", 404,
					"User with user Id " + userId + " Not Found", exceptionLink)).build();
		}else {
			//noinspection unchecked
			List<UserBean> entity = (List<UserBean>) userDetailsInCommon.getEntity();
		entity.iterator();
		for (UserBean userBean : entity) {
			String userName = ((user.getUserName() == null) || (user.getUserName().equalsIgnoreCase("")))
					? (userBean.getUserName())
					: (user.getUserName());
			String firstName = ((user.getFirstName() == null) || (user.getFirstName().equalsIgnoreCase("")))
					? (userBean.getFirstName())
					: (user.getFirstName());
			String lastName = ((user.getLastName() == null) || (user.getLastName().equalsIgnoreCase("")))
					? (userBean.getLastName())
					: (user.getLastName());
			String emailId = ((user.getEmailId() == null) || (user.getEmailId().equalsIgnoreCase("")))
					? (userBean.getEmailId())
					: (user.getEmailId());
			String phoneNumber = ((user.getPhoneNumber() == null) || (user.getPhoneNumber().equalsIgnoreCase("")))
					? (userBean.getPhoneNumber())
					: (user.getPhoneNumber());
			String instituteName = ((user.getInstituteName() == null) || (user.getInstituteName().equalsIgnoreCase("")))
					? (userBean.getInstituteName())
					: (user.getInstituteName());

			String updateUser = "UPDATE `quizapi`.`quiz_users` SET `userName` = '" + userName + "',`firstName` = '"
					+ firstName + "',`lastName` = '" + lastName + "' ,`emailId` ='" + emailId + "',`phoneNumber` = '"
					+ phoneNumber + "',`instituteName` = '" + instituteName + "' WHERE `userId` = " + userId + "";

			try {
				Connection conn = ApiUtils.getDbConnection();
				PreparedStatement prepareStatement = conn.prepareStatement(updateUser);
				int update = prepareStatement.executeUpdate();
				if (update >= 1) {
					return getUserDetailsInCommon((ConstantUtils.USERS_ID + userId), userId);
				} else {
					exceptionLink = new ArrayList<>();
					exceptionLink.add(HateoasUtils.getSelfDetails());
					return Response.status(Status.BAD_REQUEST).entity(new CustomException("User update failed", 400,
							"User with user id " + userId + " has not been updated", exceptionLink)).build();
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
				throw new ExceptionOccurred();
			}
		}
		}
		throw new ExceptionOccurred();
	}


	public Response deleteUser(int userId) throws ExceptionOccurred {
		Response userDetailsInCommon = getUserDetailsInCommon(ConstantUtils.USERS_ID + userId, userId);
		if (userDetailsInCommon.getStatus() == 200) {
			try {
				dbConnection = ApiUtils.getDbConnection();
				Statement createStatement = dbConnection.createStatement();
				int executeUpdate = createStatement.executeUpdate(ConstantUtils.USER_DELETE + userId);
				if (executeUpdate == 1) {
					exceptionLink = new ArrayList<>();
					exceptionLink.add(HateoasUtils.getSelfDetails());
					return Response.status(Status.OK).entity(new CustomException("User Deleted", 200,
							"User with user Id " + userId + " deleted", exceptionLink)).build();
				} else {
					exceptionLink = new ArrayList<>();
					exceptionLink.add(HateoasUtils.getSelfDetails());
					return Response.status(Status.BAD_REQUEST).entity(new CustomException("User not Deleted", 400,
							"User with user Id " + userId + " Not deleted", exceptionLink)).build();
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
				throw new ExceptionOccurred();
			}
		} else {
			exceptionLink = new ArrayList<>();
			exceptionLink.add(HateoasUtils.getSelfDetails());
			return Response.status(Status.NOT_FOUND).entity(new CustomException("User not Found", 404,
					"User with user Id " + userId + " Not Found", exceptionLink)).build();
		}
	}

	private static Response getUserDetailsInCommon(String statement, int id)
			throws ExceptionOccurred {

		userList = new ArrayList<>();
		try {
			dbConnection = ApiUtils.getDbConnection();
			PreparedStatement pst = dbConnection.prepareStatement(statement);
			ResultSet result = pst.executeQuery();

			LOG.info("Executing query on database for user : " + id);
			while (result.next()) {
				UserBean user = new UserBean();
				List<Links> links = new ArrayList<>();

				user.setUserId(result.getInt("userId"));
				user.setUserName(result.getString("userName"));
				user.setFirstName(result.getString("firstName"));
				user.setLastName(result.getString("lastName"));
				user.setEmailId(result.getString("emailId"));
				user.setPhoneNumber(result.getString("phoneNumber"));
				user.setInstituteName(result.getString("instituteName"));

				if (id == 0) {
					// links.add(HateoasUtils.getAlluserDetails(uriInfo));
					relMessage = "getUserById";
					links.add(HateoasUtils.getDetailsById(result.getInt("userId"),relMessage));
				} else {
					links.add(HateoasUtils.getSelfDetails());
				}
				user.setLinks(links);

				userList.add(user);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new ExceptionOccurred();
		}
		LOG.info("Number of User retrieved from the database is " + userList.size());
		if (userList.size() > 0) {
			return Response.status(Status.OK).entity(new GenericEntity<List<UserBean>>(userList) {
			}).build();
		} else {
			exceptionLink = new ArrayList<>();
			exceptionLink.add(HateoasUtils.getSelfDetails());
			return Response.status(Status.NOT_FOUND).entity(
					new CustomException("User Not Found", 404, "User with user id " + id + " not found", exceptionLink))
					.build();
		}

	}

	public Response getExamsByExamAndUserId(int userId) throws ExceptionOccurred {
		return getCommonExams(userId, 0);
	}

	public Response getExamsByExamId(int userId, int examId) throws ExceptionOccurred {
		return getCommonExams(userId, examId);
	}

	public Response getCommonExams(int userId, int examId) throws ExceptionOccurred {
		examList = new ArrayList<>();
		try {
			dbConnection = ApiUtils.getDbConnection();
			PreparedStatement pst;
			if(examId == 0) {
				pst = dbConnection.prepareStatement(ConstantUtils.USERS_EXAMS + userId);
				LOG.info("Executing query on database for user : " + userId);
			}else {
				pst= dbConnection.prepareStatement(ConstantUtils.EXAMS_ID +examId +" and userId ="+ userId);
				 LOG.info("Executing query on database for user : " + userId + " for exam " + examId);
			}
			ResultSet result = pst.executeQuery();
			//LOG.info("result.getFetchSize()" + result.getRow());
			while (result.next()) {
				ExamBean exam = new ExamBean();
				List<Links> links = new ArrayList<>();
				relMessage = "getExamsById";
				exam.setExamId(result.getInt("examId"));
				exam.setExamName(result.getString("examName"));
				exam.setExamDuration(result.getInt("examDuration"));
				exam.setNegativeMarks(result.getInt("negativeMarks"));
				exam.setNumberOfQuestions(result.getInt("numberOfQuestions"));
				exam.setExamStatus(result.getString("examStatus"));
				
				//LOG.info("result.getFetchSize() -1 " + result.getRow()+ " "+result.getInt(1));
				if(examId == 0) {
					links.add(HateoasUtils.getDetailsById(result.getInt(1), relMessage));
				}else if(result.getRow() == 1){
					links.add(HateoasUtils.getDetailsById(result.getInt(1), relMessage));
				}else {
					links.add(HateoasUtils.getSelfDetails());
				}
				exam.setLinks(links);
				examList.add(exam);
			}
		} catch (Exception e) {
			//LOG.error(e.getMessage());
			e.printStackTrace();
			throw new ExceptionOccurred();
		}
		//LOG.info("Number of Exams retrieved from the database is " + examList.size());
		if (examList.size() > 0) {
			return Response.status(Status.OK).entity(new GenericEntity<List<ExamBean>>(examList) {
			}).build();
		} else {
			exceptionLink = new ArrayList<>();
			exceptionLink.add(HateoasUtils.getSelfDetails());
			return Response.status(Status.NOT_FOUND).entity(new CustomException("No Exams Found", 404,
					"we dont find exams for above details", exceptionLink)).build();
		}
	}
}
