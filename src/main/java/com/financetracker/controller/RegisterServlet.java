package com.financetracker.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.users.User;
import com.financetracker.model.users.UserDAO;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		User user = null;
		user = new User(email, pass);
		
			try {
				if(UserDAO.getInstance().register(user)>0) {
					response.sendRedirect("./login.jsp");
				}else {
					response.sendRedirect("./index");
				}
			} catch (UserException e) {
				e.printStackTrace();
			}
		}
	}

