package com.financetracker.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.users.UserDAO;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		String email = request.getParameter("email");
//		String password = request.getParameter("password");
//
//		UserDAO dao = UserDAO.getInstance();
//
//		try {
//			if (dao.login(email, password)) {
//				HttpSession session = request.getSession();
//				session.setAttribute("email", email);
//				response.getWriter().println("Bravo ti se logna");
//				// response.sendRedirect("./home.jsp");
//			} else {
//				response.getWriter().println("Bravo ti ne se logna");
//				// response.sendRedirect("./register.jsp");
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
//	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserDAO dao = UserDAO.getInstance();
		try {
			if (dao.login(email, password)) {
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
//				session.setMaxInactiveInterval(5);
				
				request.getRequestDispatcher("WEB-INF/views/jsp/home.jsp").forward(request, response);
			
				//response.sendRedirect("./home.jsp");
			} else {
//				response.getWriter().println("<h1> Ti ne se logna, syjalqvam! </h1>");	
				request.getRequestDispatcher("WEB-INF/views/jsp/register.jsp").forward(request, response);

				//response.sendRedirect("./register.jsp");
			}
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
