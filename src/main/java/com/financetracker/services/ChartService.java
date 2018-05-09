package com.financetracker.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.BudgetException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.budget.BudgetDAO;
import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.users.User;

@Service
public class ChartService {

	@Autowired
	private BudgetDAO budgetDAO;
	@Autowired
	private AccountDAO accountDAO;

//	public TreeMap<String, List<Transaction>> getFilteredCashFlowStructure(User user, LocalDateTime from,
//			LocalDateTime to, String type) throws BudgetException {
//		TreeMap<String, List<Transaction>> result = new TreeMap<>();
//		Map<String, List<Transaction>> transactionsAllAccounts;
//		try {
//			transactionsAllAccounts = budgetDAO.allTransactionsAllAccounts(user);
//			for (String account : transactionsAllAccounts.keySet()) {
//				List<Transaction> transactions = new ArrayList<Transaction>();
//				for (Transaction transaction : transactionsAllAccounts.get(account)) {
//					if (transaction.getDate().isAfter(from) && transaction.getDate().isBefore(to)) {
//						transactions.add(transaction);
//					}
//				}
//				result.put(account, transactions);
//			}
//
//			return result;
//		} catch (BudgetException e) {
//			e.printStackTrace();
//			throw new BudgetException("No accounts or transactions found for this user!");
//		}
//	}

	public Double calculateAllBalance(Set<Account> accounts) {
		double allBalance = 0.0;
		for (Account account : accounts) {
			allBalance += account.getBalance();
		}
		return allBalance;
	}

	// public Map<LocalDate, Double> getGraphData(User user, Double allBalance) {
	// Map<LocalDate, Double> defaultTransactions =
	// getTransactionAmountAndDate(user);
	// Map<LocalDate, BigDecimal> reverseDefaultTransactions = new
	// TreeMap<LocalDate, BigDecimal>(Collections.reverseOrder());
	// reverseDefaultTransactions.putAll(defaultTransactions);
	//
	// for (LocalDate date : reverseDefaultTransactions.keySet()) {
	// BigDecimal transactionAmount = reverseDefaultTransactions.get(date);
	// allBalance = allBalance.subtract(transactionAmount);
	// reverseDefaultTransactions.put(date, transactionAmount.add(allBalance));
	// }
	//
	// Map<LocalDate, BigDecimal> finalDefaultTransactions = new TreeMap<LocalDate,
	// BigDecimal>();
	// finalDefaultTransactions.putAll(reverseDefaultTransactions);
	//
	// return finalDefaultTransactions;
	// }
	//
	
	
	
	
	
	
	
	
	
	
//	public Map<String, Map<LocalDate, Double>> getTransactionAmountAndDate(User user) throws BudgetException {
//		Map<String, Map<LocalDate, Double>> result = new TreeMap<String, Map<LocalDate, Double>>();
//		// TreeSet<Account> accounts=new
//		// TreeSet<Account>(accountDAO.getAllAccountsForUser(user));
//		Map<Account, List<Transaction>> accountTransactions = budgetDAO.allTransactionsAllAccounts(user);
//		for (Account account : accountTransactions.keySet()) {
//			Map<LocalDate, Double> dateAmountForAccount = new TreeMap<LocalDate, Double>();
//			for (Transaction transaction : accountTransactions.get(account)) {
//				double amount = 0;
//				LocalDate date = transaction.getDate().toLocalDate();
//				if (dateAmountForAccount.containsKey(date)) {
//					amount = dateAmountForAccount.get(date);
//				} else {
//					amount = account.getBalance();
//					if (transaction.getIsIncome()) {
//						dateAmountForAccount.put(transaction.getDate().toLocalDate(), amount + transaction.getAmount());
//					} else {
//						dateAmountForAccount.put(transaction.getDate().toLocalDate(), amount - transaction.getAmount());
//					}
//				}
//
//			}
//			result.put(account.getAccountName(), dateAmountForAccount);
//		}
//		return result;
//	}
}
