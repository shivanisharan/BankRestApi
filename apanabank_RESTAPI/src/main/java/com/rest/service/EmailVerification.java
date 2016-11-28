package com.rest.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rest.dao.CustomerDao;
import com.rest.dao.CustomerDaoImpl;

/**
 * Servlet implementation class EmailVerification
 */
@WebServlet("/EmailVerification")
public class EmailVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmailVerification() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("userName");
		String hash = request.getParameter("hash");
		String scope = request.getParameter("scope");
		int uid = Integer.parseInt(request.getParameter("userID"));
		session = request.getSession();
		session.setAttribute("userName", uname);
		CustomerDao customerDao = new CustomerDaoImpl();
		String status = customerDao.getStatus(uid);
		if (status.equalsIgnoreCase("Inactive")) {
			
			System.out.println(uname + ":" + hash + ":" + scope + ":" + uid);
			String message = null;
			try {
				if (customerDao.userEmailVerification(hash, uid) && scope.equals("activation")) {
					customerDao.updateStatus(uid);
					message = "Email verified successfully. Account was activated. Click <a href=#>here</a> to login";
				} else {
					message = "wrong email verification input";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (message != null && !message.equalsIgnoreCase("wrong email verification input")) {
				request.setAttribute("message", message);
				request.getRequestDispatcher("/frontEnd/views/success.jsp").forward(request, response);
			}
		}else{
			request.getRequestDispatcher("/frontEnd/views/error.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
