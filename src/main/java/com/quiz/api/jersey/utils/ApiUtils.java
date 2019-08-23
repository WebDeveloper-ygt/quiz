package com.quiz.api.jersey.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ApiUtils {

	private static Logger LOG=Logger.getLogger(ApiUtils.class);
	private static final String URL = "jdbc:mysql://localhost:3306/quizapi";
	private static final String USER = "admin";
	private static final String PASSWORD = "admin";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static Connection connection;
	
	public ApiUtils() {
		LOG.info("Invoked " +this.getClass().getName());
	}

	public static Connection getDbConnection() throws SQLException, ClassNotFoundException {

		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			LOG.info("Connection happened");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
		
	}
}
