package com.financetracker.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.budget.Budget;
import com.financetracker.model.budget.BudgetDAO;
import com.financetracker.model.users.User;

@Controller
@RequestMapping(value = "/budget")
public class BudgetController {

	@Autowired
	private BudgetDAO budgetDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getBudget(HttpSession session) {

		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			try {
				Budget budget = budgetDao.getBudget(user);
				session.setAttribute("budget", budget);
				return "budget";
			} catch (UserException e) {
				return "error";
			}
		}
		return "error";
	}
}
