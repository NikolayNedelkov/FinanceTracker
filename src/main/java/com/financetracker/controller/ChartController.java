package com.financetracker.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financetracker.exceptions.BudgetException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.budget.BudgetDAO;
import com.financetracker.model.users.User;
import com.financetracker.services.ChartService;

@Controller
@RequestMapping(value = "/charts")
public class ChartController {

	@Autowired
	private ChartService chartService;
	@Autowired
	BudgetDAO budgetDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getCashflowTrend(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");

		Map<String, List<Double>> chartData;
		try {
			chartData = budgetDao.getStatisticsAllAccounts(user);
			if (chartData.isEmpty()) {
				return "empty";
			}
			ArrayList<String> accountNames = new ArrayList<String>();
			for (String name : chartData.keySet()) {
				accountNames.add(name);
			}
			model.addAttribute("accounts", accountNames);
			List<Double> balance = new ArrayList<Double>();
			for (String accountName : chartData.keySet()) {
				balance.add(chartData.get(accountName).get(4));
			}
			model.addAttribute("balance", balance);
			return "charts";
		} catch (BudgetException e) {
			e.printStackTrace();
			return "empty";
		}
	}
}
