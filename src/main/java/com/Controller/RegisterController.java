package com.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Connection.DBConnectionProvider;
import com.Entity.Pass;
import com.Helper.PasswordEncryptDecrypt;
import com.Model.PassDAOIMPL;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PassDAOIMPL passDAOIMPL;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
		passDAOIMPL = new PassDAOIMPL();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String secretKey = "secrete";
			PasswordEncryptDecrypt passwordEncryptDecrypt = new PasswordEncryptDecrypt();

			String username = request.getParameter("username");
			String password = request.getParameter("password_");

			String encryptedPass = passwordEncryptDecrypt.encode(password, secretKey);

			Pass pass = new Pass();
			pass.setUsername(username);
			pass.setPassword(encryptedPass);

			boolean finalInsertedPass = passDAOIMPL.insertPass(pass);
			if (finalInsertedPass == true) {
				System.out.println("ENCRYPTED " + encryptedPass);
				response.sendRedirect("login.jsp");
			} else {
				System.out.println("Error....");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			PreparedStatement preparedStatement = null;
			Connection connection = null;
			DBConnectionProvider.closeResources1(preparedStatement, connection);
		}

	}

}
