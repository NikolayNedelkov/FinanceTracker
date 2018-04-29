package com.financetracker.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.financetracker.exceptions.AccountException;
import com.financetracker.model.accountType.AccountTypeDAO;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.currencies.CurrencyDAO;
import com.financetracker.model.users.User;

/**
 * Servlet implementation class AddAccountServlet
 */
@WebServlet("/addAccount")
public class AddAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/jsp/addNewAccount.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// oshte validaciq posle
		if (request.getSession(false) == null) {
			response.sendRedirect("WEB-INF/views/jsp/home.jsp");
		}
		try {
			String name = (String) request.getParameter("account name");
			Double balance = Double.parseDouble(request.getParameter("balance"));
			String accountNumber = (String) request.getParameter("last 4 digits");
			System.out.println((String) request.getParameter("currency"));
			int currencyId = CurrencyDAO.getInstance().getCurrencyId((String) request.getParameter("currency"));
			int accountTypeID = AccountTypeDAO.getInstance().getAccountTypeId((String) request.getParameter("type"));
			User user = (User) request.getSession().getAttribute("user");
			Account account = new Account(user, name, balance, accountNumber, 0f, 0, currencyId, accountTypeID);
			if (AccountDAO.getInstance().addNewAccount(account) > 0) {
				request.getRequestDispatcher("WEB-INF/views/jsp/accounts.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "The account couldn't be added!");
				request.getRequestDispatcher("WEB-INF/views/jsp/accounts.jsp").forward(request, response);
			}
		} catch (ClassNotFoundException | SQLException | AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
