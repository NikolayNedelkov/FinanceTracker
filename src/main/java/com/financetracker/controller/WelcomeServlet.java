package com.financetracker.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("")
public class WelcomeServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession s = request.getSession();
		Object o = s.getAttribute("logged");
		boolean logged = (o != null && ((boolean) o));
		if (s.isNew() || !logged) {
			request.getRequestDispatcher("WEB-INF/views/jsp/login.jsp").forward(request, response);
			
			//response.sendRedirect("static/index.html");
		} else {
			request.getRequestDispatcher("WEB-INF/views/jsp/home.jsp").forward(request, response);
			// request.getRequestDispatcher("promocia.jsp").forward(request, response);
			return;
		}
	}

}
