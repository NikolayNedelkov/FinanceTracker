package com.financetracker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.BudgetException;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.budget.Budget;
import com.financetracker.model.budget.BudgetDAO;
import com.financetracker.model.users.User;

@Controller
@RequestMapping(value = "/budget")
public class BudgetController {

	@Autowired
	private BudgetDAO budgetDao;
	@Autowired
	private AccountDAO accountDAO;

	@RequestMapping(method = RequestMethod.GET)
	public String getBudget(HttpSession session, Model model) {

		User user = (User) session.getAttribute("user");
		try {
			// get all budgets for all accounts from the BudgetDAO
			Map<String, List<Double>> budgetByAccount = budgetDao.getBudgetByAccount(user);
			model.addAttribute("budgetByAccount", budgetByAccount);

//			for (Integer accountId : budgetByAccount.keySet()) {
//				// foreach key in the map add the accountname to the model, in order to display
//				// it in the view
//
//				model.addAttribute("accountName", ((Account) accountDAO.getAccountById(accountId)).getAccountName());
//				// get the actual data, the value mapped for this account (List of total incomes
//				// and outcomes)
//				List<Double> incomeOutcomeAccount = budgetByAccount.get(accountId);
//				model.addAttribute("incomes", incomeOutcomeAccount.get(0));
//				model.addAttribute("outcomes", incomeOutcomeAccount.get(1));
//			}
			return "budget";
			// Budget budget = budgetDao.getBudget(user);
			// session.setAttribute("budget", budget);
			// return "budget";
		} catch (BudgetException e) {
			e.printStackTrace();
			return "error";
		}

	}
}
