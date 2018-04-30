package com.financetracker.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.transactions.TransactionDAO;



@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ((request.getSession(false) == null) || (request.getSession().getAttribute("email") == null)) {
			response.sendRedirect("./");
			return;
		}

		try {
			String payee = request.getParameter("payee");
			double amount = Double.parseDouble(request.getParameter("amount"));
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			//int accountID = Integer.parseInt(request.getParameter("account"));
			boolean isIncome = true;
			if (request.getParameter("typeSelect").equals("withdrawal")) {
				isIncome = false;
			}
			int category = Integer.parseInt(request.getParameter("expense_categories"));
			Transaction transaction = new Transaction(payee, amount, date, 9, isIncome, category);
			TransactionDAO dao = TransactionDAO.getInstance();
			dao.addTransaction(transaction);
			RequestDispatcher dispatcher = request.getRequestDispatcher("transactions.jsp");
			dispatcher.forward(request, response);


		} catch (TransactionException | SQLException  e) {
			e.printStackTrace();
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
		}

	}

}
