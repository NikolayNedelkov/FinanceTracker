package com.financetracker.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.categories.CategoryDAO;
import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.transactions.TransactionDAO;
import com.financetracker.model.users.User;
import com.google.gson.Gson;

@Controller
@RequestMapping(value="/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String showTransactions(Model model, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "signup-login";
		}

		try {
			User currentUser = (User) session.getAttribute("user");
			List<Transaction> allUserTransactions = new ArrayList<>();
			HashSet<Account> currentUserAccounts = accountDAO.getAllAccountsForUser(currentUser);

			for (Account account : currentUserAccounts) {
				allUserTransactions.addAll(transactionDAO.getAllTransactions(account));
			}
			model.addAttribute("allUserTransactions", allUserTransactions);
		} catch (AccountException | TransactionException | SQLException e) {
			e.printStackTrace();
			return "error";
		}
		return "transactions";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	protected String getCategories(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			int transactionType = Integer.parseInt(request.getParameter("typeSelect"));
			TreeSet<String>categories = categoryDAO.getCategories(transactionType);
			response.setContentType("application/json");
			response.getWriter().println(new Gson().toJson(categories));
		} catch (SQLException | TransactionException | IOException e) {
			e.printStackTrace();
			return "error";
		}
		return "transactions";

	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	protected String addTransaction(@ModelAttribute Transaction readyTransaction, HttpServletRequest request, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/signup-login";
		}

		try {
			//session.getAttribute("userAccounts");
			
			String payee = request.getParameter("payee");
			double amount = Double.parseDouble(request.getParameter("amount"));
			LocalDate date = LocalDate.parse(request.getParameter("date"));
			//int accountID = Integer.parseInt(request.getParameter("account"));
			boolean isIncome = true;
			if (request.getParameter("typeSelect").equals("withdrawal")) {
				isIncome = false;
			}
			int category = Integer.parseInt(request.getParameter("expense_categories"));
			//Transaction transaction = new Transaction(payee, amount, date, , category, isIncome);
			
			transactionDAO.addTransaction(readyTransaction);
			
			return "redirect:/transactions";

		} catch (TransactionException | SQLException  e) {
			e.printStackTrace();
			return "error";
		}

	}
}