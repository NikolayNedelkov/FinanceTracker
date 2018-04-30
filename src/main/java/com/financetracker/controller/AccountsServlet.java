package com.financetracker.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.financetracker.exceptions.AccountException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.users.User;

/**
 * Servlet implementation class AccountsServlet
 */
@WebServlet("/accounts")
public class AccountsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// AccountDAO.getInstance().getAllAccountsForUser();
		// redirect???
		if ((request.getSession() == null) || (request.getSession().getAttribute("user") == null)) {
			request.getRequestDispatcher("WEB-INF/views/jsp/login.jsp").forward(request, response);
			return;
		}
		try {
			User loggedUser = (User) request.getSession().getAttribute("user");
			HashSet<Account> usersAccounts = AccountDAO.getInstance().getAllAccountsForUser(loggedUser);
			loggedUser.setAccounts(usersAccounts);
			request.getRequestDispatcher("WEB-INF/views/jsp/accounts.jsp").forward(request, response);
		} catch (ClassNotFoundException | AccountException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		request.getSession().getAttribute("email");
//		request.getRequestDispatcher("WEB-INF/views/jsp/accounts.jsp").forward(request, response);
	}
}
