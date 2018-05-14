package com.financetracker.controller;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.financetracker.exceptions.AccountException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.transactions.PlannedTransactionThread;
import com.financetracker.model.users.EmailNotificationThread;
import com.financetracker.model.users.User;

@Controller
@RequestMapping(value = "/home")
public class HomeController {

	@Autowired
	private AccountDAO accountDao;
	@Autowired
	private EmailNotificationThread emialNotificationThread;

	@RequestMapping(method = RequestMethod.GET)

	public String goHome(@RequestParam(required = false, value = "criteria") String criteria,
			HttpServletRequest request, HttpSession session, Model model) {
		if (!emialNotificationThread.isAlive()) {
			emialNotificationThread.start();
		}
		if ((session == null) || (session.getAttribute("user") == null)) {
			return "redirect:/";
		}
		User user = (User) session.getAttribute("user");
		if (criteria != null) {
			try {
				Set<Account> sortedAccounts = accountDao.getAccountsBySort(user, criteria);
				model.addAttribute("sorted", sortedAccounts);
				model.addAttribute("criteria", criteria);
				return "sorted";
			} catch (AccountException e) {
				e.printStackTrace();
				return "home";
			}
		}
		return "home";
	}

}
