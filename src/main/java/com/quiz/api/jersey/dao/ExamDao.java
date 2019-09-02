package com.quiz.api.jersey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.ExamBean;
import com.quiz.api.jersey.service.ExamService;
import com.quiz.api.jersey.utils.ApiUtils;
import com.quiz.api.jersey.utils.ConstantUtils;
import com.quiz.api.jersey.utils.HateoasUtils;
import com.quiz.api.jersey.utils.Links;

public class ExamDao{

	private final Logger LOG = Logger.getLogger(ExamDao.class);
	private static final UserDao userDao = new UserDao();
	private static Connection dbConnection;
	private static List<Links> examLinks = new ArrayList<>();
	@Context
	UriInfo uriInfo;
	public ExamDao() {
		LOG.info("Invoked " + this.getClass().getName());
	}

	public Response addExams(ExamBean examBean, int userId) throws ExceptionOccurred {
		try {
			dbConnection = ApiUtils.getDbConnection();
			String addExams = "INSERT INTO `quiz_exams`(`examName`,`examDuration`,`negativeMarks`,`numberOfQuestions`,`userId`)VALUES(?,?,?,?,?)";
			PreparedStatement pst = dbConnection.prepareStatement(addExams, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, examBean.getExamName());
			pst.setInt(2, examBean.getExamDuration());
			pst.setInt(3, examBean.getNegativeMarks());
			pst.setInt(4, examBean.getNumberOfQuestions());
			pst.setInt(5, userId);

			int result = pst.executeUpdate();
			LOG.info(result);
			if (result == 1) {
				ResultSet generatedKeys = pst.getGeneratedKeys();
				while (generatedKeys.next()) {
					int genId = generatedKeys.getInt(1);
					return userDao.getCommonExams(userId, genId);
				}
			} else {
				Links selfDetails = HateoasUtils.getSelfDetails(uriInfo);
				examLinks.add(selfDetails);
				CustomException customException = new CustomException("Exam Not added", 400,
						"Bad Request found exam not added", examLinks);
				return Response.status(Status.BAD_REQUEST).entity(customException).build();
			}
		} catch (Exception e) {
			throw new ExceptionOccurred();
		}

		Links selfDetails = HateoasUtils.getSelfDetails(uriInfo);
		examLinks.add(selfDetails);
		CustomException customException = new CustomException("Exam Not added", 400, "Bad Request found exam not added",
				examLinks);
		return Response.status(Status.BAD_REQUEST).entity(customException).build();
	}


	public Response updateExams(ExamBean examBean, int userId, int examId)
			throws ExceptionOccurred {
		Response commonExams = userDao.getCommonExams( userId, examId);
		if (commonExams.getStatus() != 200) {
			examLinks = new ArrayList<>();
			examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
			return Response.status(Status.NOT_FOUND).entity(new CustomException("Exam Not Found", 404,
					"Exam for the ExamID " + examId + " Not Found", examLinks)).build();
		} else {
			//noinspection unchecked
			List<ExamBean> entity = (List<ExamBean>) commonExams.getEntity();
			for (ExamBean exbean : entity) {
				String examName = (examBean.getExamName() == null || examBean.getExamName().equalsIgnoreCase(""))
						? (exbean.getExamName())
						: (examBean.getExamName());
				int examDuration = (examBean.getExamDuration() == 0) ? (exbean.getExamDuration())
						: (examBean.getExamDuration());
				int negativeMarks = (examBean.getNegativeMarks() == 0) ? (exbean.getNegativeMarks())
						: (examBean.getNegativeMarks());
				int numQuestions = (examBean.getNumberOfQuestions() == 0) ? (exbean.getNumberOfQuestions())
						: (examBean.getNumberOfQuestions());
				String status = (examBean.getExamStatus() == null || examBean.getExamStatus().equalsIgnoreCase(""))
						? (exbean.getExamStatus())
						: (examBean.getExamStatus());

				String updateExam = "UPDATE `quizapi`.`quiz_exams` SET `examName` = '" + examName
						+ "',`examDuration` = '" + examDuration + "',`negativeMarks` ='" + negativeMarks
						+ "' ,`numberOfQuestions` = '" + numQuestions + "',`examStatus` ='" + status
						+ "' WHERE `examId` = '" + examId + "' AND `userId` = '" + userId + "'";

				try {
					dbConnection = ApiUtils.getDbConnection();
					PreparedStatement updateExams = dbConnection.prepareStatement(updateExam);
					int executeUpdate = updateExams.executeUpdate();
					LOG.info("Update Counts :" +executeUpdate);
					if (executeUpdate >= 1) {
						return userDao.getCommonExams(userId, examId);
					} else {
						examLinks = new ArrayList<>();
						examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
						return Response.status(Status.BAD_REQUEST).entity(new CustomException("Exam update failed", 400,
								"Exam with  examId " + examId + " has not been updated", examLinks)).build();
					}
				} catch (Exception e) {
					LOG.error(e.getMessage());
					throw new ExceptionOccurred();
				}

			}
		}
		throw new ExceptionOccurred();
	}


	public Response deleteExams(int userId, int examId) throws ExceptionOccurred {
		Response commonExams = userDao.getCommonExams(userId, examId);
		if (commonExams.getStatus() != 200) {
			examLinks = new ArrayList<>();
			examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
			return Response.status(Status.NOT_FOUND).entity(new CustomException("Exam Not Found", 404,
					"Exam for the ExamID " + examId + " Not Found", examLinks)).build();
		} else {
			try {
			dbConnection = ApiUtils.getDbConnection();
			Statement createStatement = dbConnection.createStatement();
			int executeUpdate = createStatement.executeUpdate(ConstantUtils.EXAM_DELETE+examId+" and userId="+userId);
			if(executeUpdate == 1) {
				examLinks = new ArrayList<>();
				examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
				return Response.status(Status.OK).entity(new CustomException("Exam Deleted", 200,
						"Eaxm with  examId " + examId + " for user "+userId+ " has been deleted", examLinks)).build();
			}else {
				examLinks = new ArrayList<>();
				examLinks.add(HateoasUtils.getSelfDetails(uriInfo));
				return Response.status(Status.NOT_FOUND).entity(new CustomException("Exam Not Deleted", 400,
						"Eaxm with  examId " + examId + " for user "+userId+ " has not deleted", examLinks)).build();
			}
			}catch (Exception e) {
				LOG.error(e.getMessage());
				throw new ExceptionOccurred();
			}
		}
	}

}
