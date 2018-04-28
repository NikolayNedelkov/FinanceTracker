package com.financetracker.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.financetracker.model.accounts.AccountDAO;

/**
 * Servlet implementation class AccountsServlet
 */
@WebServlet("/accounts")
public class AccountsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//AccountDAO.getInstance().getAllAccountsForUser();
		request.getSession().getAttribute("email");
		request.getRequestDispatcher("WEB-INF/views/jsp/accounts.jsp").forward(request, response);
	}
}
