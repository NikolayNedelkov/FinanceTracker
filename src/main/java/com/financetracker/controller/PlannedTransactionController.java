package com.financetracker.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CategoryException;
import com.financetracker.exceptions.PlannedTransactionException;
import com.financetracker.exceptions.RecurrencyException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.IAccountDAO;
import com.financetracker.model.categories.ICategoryDAO;
import com.financetracker.model.transactions.IPlannedTransactionDAO;
import com.financetracker.model.transactions.PlannedTransaction;
import com.financetracker.model.users.User;
import com.financetracker.recurrencies.IRecurrencyDAO;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/plannedTransactions")
public class PlannedTransactionController {

	@Autowired
	public IPlannedTransactionDAO plannedTransactionDAO;
	@Autowired
	public IAccountDAO accountDAO;
	@Autowired
	public ICategoryDAO categoryDAO;
	@Autowired
	public IRecurrencyDAO recurrencyDAO;
	

	@RequestMapping(method = RequestMethod.GET)
	protected String showPlannedTransactions(Model model, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/";
		}

		try {
			SortedSet<String> categories = categoryDAO.getAllCategories();

			User currentUser = (User) session.getAttribute("user");
			List<PlannedTransaction> allUserPlannedTransactions = plannedTransactionDAO.getAllPlannedTransactions(currentUser);	
			
			model.addAttribute("allUserPlannedTransactions", allUserPlannedTransactions);
			model.addAttribute("categories", categories);
		} catch (CategoryException | PlannedTransactionException e) {
			e.printStackTrace();
			return "error";
		}
		return "plannedTransactions";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	protected String getUserAccounts(HttpSession session,Model model) {
		User loggedUser = (User) session.getAttribute("user");
		try {
			//loading accounts of the user
			Set<Account> usersAccounts = (Set<Account>) accountDAO.getAllAccountsForUser(loggedUser);
			loggedUser.setAccounts(usersAccounts);
			//loading recurrencies
			SortedSet<String> recurrencies = recurrencyDAO.getAllRecurrencies();
			model.addAttribute("recurrencies",recurrencies);
			return "addNewPlannedTransaction";
		} catch (AccountException | RecurrencyException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	@RequestMapping(value = "/add/getCategories", method = RequestMethod.GET)
	public @ResponseBody void getCategories(HttpServletRequest request, HttpServletResponse response) throws TransactionException {
			
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
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	protected String addTransaction(HttpServletRequest request, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/signup-login";
		}

		try {
			String payee = request.getParameter("payee");
			double amount = Double.parseDouble(request.getParameter("amount"));
			LocalDate plannedDate = LocalDate.parse(request.getParameter("plannedDate"));

			boolean isIncome;
			
			if (request.getParameter("typeSelect").equals("false")) {
				isIncome = false;
			}else {
				isIncome = true;
			}
			
			String category = request.getParameter("category");
			String accountName=request.getParameter("accountSelect");
			Account account = accountDAO.getAccountByName(accountName);
			String recurrency = request.getParameter("recurrencySelect");
			
			PlannedTransaction plannedTransaction = new PlannedTransaction(payee, amount, plannedDate, account, category, isIncome,recurrency);
			plannedTransactionDAO.addTransaction(plannedTransaction);
			
			return "redirect:/plannedTransactions";

		} catch (TransactionException | AccountException | PlannedTransactionException  e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deletePlannedTransaction(@PathVariable("id") Integer id,HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/signup-login";
		}
		try {
			plannedTransactionDAO.deletePlannedTransaction(id);
			return "redirect:/plannedTransactions";
		} catch (PlannedTransactionException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
