package com.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Connection.DBConnectionProvider;
import com.Entity.Pass;

public class PassDAOIMPL implements PassDAO {

	private static final String INSERT_PASS = "insert into passtable(username,password)" + " values(?,?)";

	private static final String LOGIN = "select * from passtable where username=? and password=?";

	public boolean insertPass(Pass pass) {

		boolean insertFlag = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnectionProvider.getConnection();
			preparedStatement = connection.prepareStatement(INSERT_PASS);

			preparedStatement.setString(1, pass.getUsername());
			preparedStatement.setString(2, pass.getPassword());

			int insertedPass = preparedStatement.executeUpdate();

			if (insertedPass < 0 && insertedPass != 1 && insertedPass == 0) {
				insertFlag = true;
				System.out.println("Not inserted successfully " + insertedPass);
			} else if (insertedPass == 1 && insertedPass > 0) {
				insertFlag = true;
				System.out.println("Inserted successfully " + insertedPass);
			} else {
				System.out.println("Something went wrong on the server.Please rectify the issue and try again...");
			}
		} catch (SQLException ex) {
			DBConnectionProvider.printSQLException(ex);
		}

		return insertFlag;
	}

	public Pass login(String username, String password) {

		Pass pass = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnectionProvider.getConnection();
			preparedStatement = connection.prepareStatement(LOGIN);

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				pass = new Pass();
				pass.setId(resultSet.getInt(1));
				pass.setUsername(resultSet.getString(2));
				pass.setPassword(resultSet.getString(3));
			}
		} catch (SQLException ex) {
			DBConnectionProvider.printSQLException(ex);
		}

		return pass;
	}

}
