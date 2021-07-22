package com.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnectionProvider {

	private static final String DBURL = "jdbc:mysql://localhost:3306/pass";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private static String DRIVER;
	static {
		try {
			DRIVER = "com.mysql.cj.jdbc.Driver";
			Class.forName(DRIVER).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static Connection getConnection() {

		Connection connection = null;
		try {
			if (connection != null) {
				connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
				System.out.println("Connection is not established successfully... " + connection);
			} else if (connection == null) {
				connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
				System.out.println("Connection Established Successfully... " + connection);
			} else {
				System.out.println("Both the connections are not valid.Please check once again and try again...");
			}
		} catch (SQLException ex) {
			printSQLException(ex);
		}

		return connection;

	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQL STATE :- " + ((SQLException) e).getSQLState());
				System.err.println("ERROR CODE :- " + ((SQLException) e).getErrorCode());
				System.err.println("SQL MESSAGE :- " + ((SQLException) e).getMessage());
				Throwable thrown = ex.getCause();
				while (thrown != null) {
					System.out.println("CAUSE :- " + thrown);
					thrown = thrown.getCause();
				}
			}
		}
	}

	public static void closeResources1(PreparedStatement preparedStatement, Connection connection) {

		try {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}

			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			System.out.println("COnnection closed successfully.....");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void closeResources2(ResultSet resultSet, PreparedStatement preparedStatement,
			Connection connection) {

		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}

			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}

			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
			System.out.println("Connection closed successfully.....");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
