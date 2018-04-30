package com.financetracker.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CurrencyException;
import com.financetracker.model.accountType.AccountTypeDAO;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.currencies.CurrencyDAO;
import com.financetracker.model.users.User;

@Controller
@RequestMapping(value = "/accounts")
public class AccountController {

	@Autowired
	private AccountDAO accountDao;
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private AccountTypeDAO accountTypeDAO;

	@RequestMapping(method = RequestMethod.GET)
	public String addAccounts(Model model, HttpSession session) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "login";
		}
		try {
			User loggedUser = (User) session.getAttribute("user");
			HashSet<Account> usersAccounts = accountDao.getAllAccountsForUser(loggedUser);
			loggedUser.setAccounts(usersAccounts);
			// request.getRequestDispatcher("WEB-INF/views/jsp/accounts.jsp").forward(request,
			// response);
			return "accounts";
		} catch (AccountException e) {
			e.printStackTrace();
			return "error";
		}
	}

	// GET i POST metodi za dobavqne na nov Account, URL:.../accounts/add

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addAccount(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		List<String> currencies;
		List<String> accountTypes;
		try {
			currencies = currencyDAO.getCurrenciesFromDB();
			accountTypes = accountTypeDAO.getAccountTypesFromDB();
			model.addAttribute("allCurrencies", currencies);
			model.addAttribute("allTypes", accountTypes);
			return "addNewAccount";
		} catch (ClassNotFoundException | SQLException | CurrencyException | AccountException e) {
			e.printStackTrace();

			// tuka nqma da e home, vremenno
			return "redirect:home";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addAccount(@ModelAttribute Account readyAccount, HttpServletRequest request) {
		try {
			accountDao.addNewAccount(readyAccount);
			if (accountDao.addNewAccount(readyAccount) > 0) {
				return "accounts";
			} else {
				request.setAttribute("error", "The account couldn't be added!");
				return "accounts";
			}
		} catch (AccountException e) {
			e.printStackTrace();
			return "error";
		}
	}
}
