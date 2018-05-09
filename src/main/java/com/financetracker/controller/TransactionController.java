package com.financetracker.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CategoryException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.IAccountDAO;
import com.financetracker.model.categories.ICategoryDAO;
import com.financetracker.model.transactions.ITransactionDAO;
import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.users.User;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/transactions")
public class TransactionController {

	@Autowired
	public ITransactionDAO transactionDAO;
	@Autowired
	public IAccountDAO accountDAO;
	@Autowired
	public ICategoryDAO categoryDAO;

	@RequestMapping(method = RequestMethod.GET)
	protected String showTransactions(Model model, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/";
		}

		try {
			SortedSet<String> categories = categoryDAO.getAllCategories();
			User currentUser = (User) session.getAttribute("user");
			List<Transaction> allUserTransactions = transactionDAO.getAllTransactions(currentUser);
			model.addAttribute("allUserTransactions", allUserTransactions);
			model.addAttribute("categories",categories);
		} catch (AccountException | TransactionException | CategoryException e) {
			e.printStackTrace();
			return "error";
		}
		return "transactions";
	}

	@RequestMapping(value = "/add/getCategories", method = RequestMethod.GET)
	public @ResponseBody void getCategories(HttpServletRequest request, HttpServletResponse response)
			throws TransactionException {

		try {
			String transactionType = request.getParameter("typeSelect");
			SortedSet<String> categories = categoryDAO.getCategoriesByType(transactionType);
			response.setContentType("application/json");
			response.getWriter().println(new Gson().toJson(categories));

		} catch (IOException | CategoryException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot get categories!");
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	protected String getUserAccounts(HttpSession session) {
			return "newTransaction";
	}

	@RequestMapping(value = "/add/category", method = RequestMethod.GET)
	protected String addCategory(HttpServletRequest request) {
		return "addNewCategory";
	}

	@RequestMapping(value = "/add/category", method = RequestMethod.POST)
	protected String addNewCategory(HttpServletRequest request) {
		try {
			String categoryName = request.getParameter("categoryName");
			boolean isIncome;
			if (request.getParameter("typeSelect").equals("false")) {
				isIncome = false;
			} else {
				isIncome = true;
			}
			categoryDAO.addNewCategory(categoryName, isIncome);
		} catch (CategoryException e) {
			e.printStackTrace();
			return "redirect:../add";
		}
		return "redirect:../add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	protected String addTransaction(HttpServletRequest request, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/signup-login";
		}

		try {
			String payee = request.getParameter("payee");
			double amount = Double.parseDouble(request.getParameter("amount"));
			//???
			LocalDate date = LocalDate.parse(request.getParameter("date"));

			boolean isIncome;

			if (request.getParameter("typeSelect").equals("false")) {
				isIncome = false;
			} else {
				isIncome = true;
			}

			String category = request.getParameter("category");
			String accountName = request.getParameter("accountSelect");
			Account account = accountDAO.getAccountByName(accountName);

			Transaction transaction = new Transaction(payee, amount, date, account, category, isIncome);
			transactionDAO.addTransaction(transaction);

			return "redirect:/transactions";

		} catch (TransactionException | AccountException | SQLException e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteTransaction(@PathVariable("id") Integer id, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/signup-login";
		}
		try {
			transactionDAO.deleteTransaction(id);
			return "redirect:/transactions";
		} catch (TransactionException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editTransaction(Model model, @PathVariable("id") Integer id,HttpSession session) {
		try {
			Transaction currentTransaction = transactionDAO.getTransactionById(id);
			model.addAttribute("currentTransaction", currentTransaction);
			
			/*Set<Account> usersAccounts = (Set<Account>) accountDAO.getAllAccountsForUser((User)session.getAttribute("user"));
			model.addAttribute("usersAccounts", usersAccounts);*/
			return "editTransaction";
		} catch (TransactionException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "/edit/{account_id}", method = RequestMethod.POST)
	public String editTransaction(@ModelAttribute Transaction newTransaction, @PathVariable("id") Integer id) {
		try {
			transactionDAO.updateTransaction(newTransaction);
			return "redirect:/transactions";

		} catch (TransactionException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value = "/edit/{account_id}/getCategories", method = RequestMethod.GET)
	public @ResponseBody void loadCategoriesForEdit(HttpServletRequest request, HttpServletResponse response)
			throws TransactionException {

		try {
			String transactionType = request.getParameter("typeSelect");
			SortedSet<String> categories = categoryDAO.getCategoriesByType(transactionType);
			response.setContentType("application/json");
			response.getWriter().println(new Gson().toJson(categories));

		} catch (IOException | CategoryException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot get categories!");
		}
	}
}