package com.financetracker.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.BudgetException;
import com.financetracker.model.budget.BudgetDAO;
import com.financetracker.model.users.User;

@Controller
@RequestMapping(value = "/budget")
public class BudgetController {

	@Autowired
	private BudgetDAO budgetDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getBudget(HttpSession session, Model model) {

		User user = (User) session.getAttribute("user");
		try {
			// get all budgets for all accounts from the BudgetDAO
			Map<String, List<Double>> budgetByAccount = budgetDao.getBudgetByAccount(user);
			model.addAttribute("budgetByAccount", budgetByAccount);
			
			return "budget";
		} catch (BudgetException e) {
			e.printStackTrace();
			return "error";
		}

	}
}