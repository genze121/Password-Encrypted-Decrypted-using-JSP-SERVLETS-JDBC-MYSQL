package com.Controller;

import java.io.IOException;

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
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PassDAOIMPL passDAOIMPL;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
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

			Pass pass = passDAOIMPL.login(username, encryptedPass);
			String decryptedPass = passwordEncryptDecrypt.decode(encryptedPass, secretKey);

			if (pass != null) {
				System.out.println("DECRYPTED " + decryptedPass);
				response.sendRedirect("welcome.jsp");
			} else {
				System.out.println("Error....");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			DBConnectionProvider.closeResources2(null, null, null);
		}
	}

}
