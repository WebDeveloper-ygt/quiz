package com.quiz.api.jersey.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.quiz.api.jersey.exception.CustomException;
import org.apache.log4j.Logger;

public class ApiUtils {

	private static final Logger LOG=Logger.getLogger(ApiUtils.class);
	private static final String URL = "jdbc:mysql://mysql:3306/quizdb";
	private static final String L_URL = "jdbc:mysql://localhost:3306/quizapi";
	private static final String USER = "admin";
	private static final String PASSWORD = "admin";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static Connection connection;
	private static  CustomException exception;
	private ApiUtils() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	public static Connection getDbConnection() throws ClassNotFoundException, CustomException {

		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			LOG.info("Connection happened");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			throw new CustomException(e.getMessage(), 500, "Error occurred while connecting database", null);
		}
		
		return connection;
		
	}
}
