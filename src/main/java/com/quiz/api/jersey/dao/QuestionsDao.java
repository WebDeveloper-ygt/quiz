package com.quiz.api.jersey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.quiz.api.jersey.exception.CustomException;
import com.quiz.api.jersey.exception.ExceptionOccurred;
import com.quiz.api.jersey.model.QuestionBean;
import com.quiz.api.jersey.model.QuestionOptionBean;
import com.quiz.api.jersey.service.QuestionsService;
import com.quiz.api.jersey.service.impl.QuestionsServiceImpl;
import com.quiz.api.jersey.utils.ApiUtils;
import com.quiz.api.jersey.utils.ConstantUtils;
import com.quiz.api.jersey.utils.HateoasUtils;
import com.quiz.api.jersey.utils.Links;

public class QuestionsDao{

	private static final Logger LOG = Logger.getLogger(QuestionsServiceImpl.class);
	private static ExamDao examDao = new ExamDao();
	private static UserDao userDao = new UserDao();
	private static Connection dbConnection;
	private static List<QuestionBean> questionList;
	public QuestionsDao() {
		LOG.info("Invoked " + this.getClass().getName());
	}
	

	public Response getAllQuestionsByExamId(int examId,int userId) throws ExceptionOccurred {
		return getQuestionInCommon(examId, 0);
	}

	public Response getQuestionsByQuestionId(int examId, int questionId,int userId)
			throws ExceptionOccurred {
		return getQuestionInCommon(examId, questionId);
	}

	private Response getQuestionInCommon(int examId, int questionId) throws ExceptionOccurred {
		
		questionList= new ArrayList<>();
		PreparedStatement pst;
		List<QuestionOptionBean> optionList = new ArrayList<>();
		try {
			dbConnection = ApiUtils.getDbConnection();
			if(questionId == 0) {
				pst = dbConnection.prepareStatement(ConstantUtils.QUESTIONS_EXAMID+examId);
			}else {
				pst = dbConnection.prepareStatement(ConstantUtils.QUESTIONS_EXAMID+examId+" and questionId="+questionId);
			}
			ResultSet result = pst.executeQuery();
			while(result.next()) {
				QuestionBean questBean = new QuestionBean();
				
				questBean.setQuestionId(result.getInt("questionId"));
				questBean.setQuestionType(result.getString("questionType"));
				questBean.setQuestionName(result.getString("questionName"));
				QuestionOptionBean questionOptions = getQuestionOptions(result.getInt("questionId"));
				questBean.setOptions(questionOptions);
				
				questionList.add(questBean);
			}
		}catch (Exception e) {
			LOG.info(e.getMessage());
			throw new ExceptionOccurred();
		}
		
		if (questionList.size() >= 1) {
			return Response.status(Status.OK).entity(new GenericEntity<List<QuestionBean>>(questionList) {}).build();
		} else {
			List<Links> exceptionLink = new ArrayList<>();
			exceptionLink.add(HateoasUtils.getSelfDetails());
			return Response.status(Status.NOT_FOUND).entity(
					new CustomException("Questions Not Found", 404, "Question for exam-id " +examId + " not found", exceptionLink))
					.build();
		}
	}

	public Response addQuestionsByExamId(int examId,int userId,QuestionBean questionBean) throws ExceptionOccurred {
	 
		QuestionOptionBean options = questionBean.getOptions();
		try {
		dbConnection = ApiUtils.getDbConnection();
		String addQuestion = "INSERT INTO `quizapi`.`quiz_questions`(`questionType`,`questionName`,`examId`) VALUES (?,?,?)";
		PreparedStatement pst = dbConnection.prepareStatement(addQuestion, Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, questionBean.getQuestionType());
		pst.setString(2, questionBean.getQuestionName());
		pst.setInt(3, examId);
		
		int result = pst.executeUpdate();
		if (result == 1) {
			ResultSet generatedKeys = pst.getGeneratedKeys();
			while (generatedKeys.next()) {
				int genId = generatedKeys.getInt(1);
				Response examsByExamId = createQuestionOption(options,genId);
				if(examsByExamId.getStatus() == 200) {
					return getQuestionInCommon(examId, genId);
				}else {
					
				}
			}
		} else {
			throw new ExceptionOccurred();
		}
		return null;
		}catch (Exception e) {
			throw new ExceptionOccurred();
		}
	}

	private Response createQuestionOption(QuestionOptionBean options, int genId) throws ExceptionOccurred {
		try {
			dbConnection = ApiUtils.getDbConnection();
			String addOptions = "INSERT INTO `quizapi`.`question_options`(`optionA`,`optionB`,`optionC`,`optionD`,`optionCorrect`)VALUES (?,?,?,?,?)";
			PreparedStatement pst= dbConnection.prepareStatement(addOptions,Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, options.getOption_A());
			pst.setString(2, options.getOption_B());
			pst.setString(3, options.getOption_C());
			pst.setString(4, options.getOption_D());
			pst.setString(5, options.getOption_Correct());
			
			int executeUpdate = pst.executeUpdate();
			if(executeUpdate == 1) {
				ResultSet generatedKeys = pst.getGeneratedKeys();
				while (generatedKeys.next()) {
					int genLateId = generatedKeys.getInt(1);
					if(genLateId == genId) {
						return Response.status(Status.OK).build();
					}else {
						return Response.status(Status.BAD_REQUEST).build();
					}
				}
			}
			
		}catch (Exception e) {
			throw new ExceptionOccurred();
		}
		return null;
		
	}

	public Response updateQuestionsByQuestionId(int examId, int questionId,int userId,QuestionBean questionBean) {
		// TODO Auto-generated method stub
		return null;
	}


	public Response deleteQuestionsByQuestionId(int examId, int questionId,int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	private QuestionOptionBean getQuestionOptions(int questionId) throws ExceptionOccurred {
		try {
			dbConnection = ApiUtils.getDbConnection();
			PreparedStatement pst = dbConnection.prepareStatement(ConstantUtils.QUSETION_OPTIONS+questionId);
			ResultSet result = pst.executeQuery();
			QuestionOptionBean queOption = null;
			while(result.next()) {
				queOption =new QuestionOptionBean();
				queOption.setQuestionId(questionId);
				queOption.setOption_A(result.getString("optionA"));
				queOption.setOption_B(result.getString("optionB"));
				queOption.setOption_C(result.getString("optionC"));
				queOption.setOption_D(result.getString("optionD"));
				queOption.setOption_Correct(result.getString("optionCorrect"));
			}
			return queOption;
		}catch (Exception e) {
			LOG.info(e.getMessage());
			throw new ExceptionOccurred();
		}
	}
}
