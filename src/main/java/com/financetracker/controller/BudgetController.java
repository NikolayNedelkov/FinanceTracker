package com.financetracker.controller;

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
			//System.out.println(user.getAccounts().iterator().next());
			Map<String, List<Double>> budgetByAccount = budgetDao.getStatisticsAllAccounts(user);
			model.addAttribute("budgetByAccount", budgetByAccount);
			List<Double> statisticTotal = budgetDao.getStatisticsTotal(user);
			double allAccountsOld = 0, allAccountsNew = 0;
			for (String account : budgetByAccount.keySet()) {
				allAccountsOld += budgetByAccount.get(account).get(3);
				allAccountsNew += budgetByAccount.get(account).get(4);
			}
			statisticTotal.add(allAccountsOld);
			statisticTotal.add(allAccountsNew);
			model.addAttribute("statisticTotal", statisticTotal);
			return "budget";
		} catch (BudgetException e) {
			e.printStackTrace();
			return "error";
		}

	}
}