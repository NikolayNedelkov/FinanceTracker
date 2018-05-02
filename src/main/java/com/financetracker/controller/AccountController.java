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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CurrencyException;
import com.financetracker.model.accountType.AccountTypeDAO;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountComparator;
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
	public String addAccounts(Model model, HttpSession session, HttpServletRequest request) {
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/signup-login";
		}
		try {
			User loggedUser = (User) session.getAttribute("user");
			HashSet<Account> usersAccounts = accountDao.getAllAccountsForUser(loggedUser);
			loggedUser.setAccounts(usersAccounts);
			return "redirect:/accounts";

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
			return "redirect:/home";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addAccount(@ModelAttribute Account readyAccount, HttpServletRequest request, HttpSession session) {
		try {
			if (accountDao.addNewAccount(readyAccount, session) > 0) {
				return "redirect:/accounts";
			} else {
				request.setAttribute("error", "The account couldn't be added!");
				return "redirect:/accounts";
			}
		} catch (AccountException e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/acc/{account_id}", method = RequestMethod.GET)
	public String editAccount(Model model, @PathVariable("account_id") Integer accountId) {
		try {
			Account currentAccount = accountDao.getAccountById(accountId);
			model.addAttribute("currentAccount", currentAccount);

			List<String> currencies = currencyDAO.getCurrenciesFromDB();
			List<String> accountTypes = accountTypeDAO.getAccountTypesFromDB();
			model.addAttribute("allCurrencies", currencies);
			model.addAttribute("allTypes", accountTypes);
			return "redirect:/account";
		} catch (AccountException | ClassNotFoundException | SQLException | CurrencyException e) {
			e.printStackTrace();
			return "error";
		}
	}

	// ne updateva v bazata
	@RequestMapping(value = "/acc/{account_id}", method = RequestMethod.POST)
	public String editAccount(@ModelAttribute Account updatedAccount, @PathVariable("account_id") Integer accountId) {
		try {
			// System.out.println(updatedAccount.getAccount_id());
			// AccountComparator comparator = new AccountComparator();
			// System.out.println(comparator.compare(accountDao.getAccountById(updatedAccount.getAccount_id()),
			// updatedAccount) == 0);
			//!!!!!!!! tuka e greshkata, vrushta mi stariq akaunt v bazata
//			Account updated = accountDao.getAccountById(updatedAccount.getAccount_id());
//			System.out.println(updatedAccount.getAccount_id());
			accountDao.updateAccount(updatedAccount);
			return "redirect:/accounts";

		} catch (AccountException e) {
			e.printStackTrace();
			return "error";
		}
	}
}
