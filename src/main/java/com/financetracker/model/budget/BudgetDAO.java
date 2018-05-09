package com.financetracker.model.budget;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.BudgetException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.users.User;

@Component
public class BudgetDAO {

	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private DBConnection DBConnection;

	private static final String CALCULATE_INCOME = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '1'";
	private static final String CALCULATE_EXPENSE = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '0'";

	private static final String ALL_TRANSACTIONS_TRANS_FOR_USER = "SELECT t.amount, t.`payee/payer`, t.date_paid, t.accounts_id, t.categories_id, t.is_income FROM transactions t JOIN accounts a ON (a.id=t.accounts_id) JOIN users u ON (u.id=a.user_id) WHERE u.id=?;";

	private static final String ALL_INCOMES_TRANS_FOR_ACCOUNT = "select sum(t.amount) from users u join accounts a on(u.id=a.user_id) join transactions t on (a.id=t.accounts_id) where u.id=? and a.id=? and t.is_income='1'; ";
	private static final String ALL_OUTCOMES_TRANS_FOR_ACCOUNT = "select sum(t.amount) from users u join accounts a on(u.id=a.user_id) join transactions t on (a.id=t.accounts_id) where u.id=? and a.id=? and t.is_income='0'; ";

	public double calculateIncome(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (user != null) {
				pstmt = DBConnection.getConnection().prepareStatement(CALCULATE_INCOME);
				pstmt.setInt(1, user.getId());

				ResultSet resultSet = pstmt.executeQuery();
				resultSet.next();

				return resultSet.getInt(1);
			} else {
				throw new UserException("You account budget cannot be calculated ");
			}

		} catch (SQLException e) {
			throw new UserException("You account budget cannot be calculated ", e);
		}
	}

	public double calculateExpense(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (user != null) {
				pstmt = DBConnection.getConnection().prepareStatement(CALCULATE_EXPENSE);
				pstmt.setInt(1, user.getId());

				ResultSet resultSet = pstmt.executeQuery();
				resultSet.next();

				return resultSet.getInt(1);
			} else {
				throw new UserException("You account budget cannot be calculated ");
			}

		} catch (SQLException e) {
			throw new UserException("You account budget cannot be calculated ", e);
		}
	}

	public Budget getBudget(User u) throws UserException {
		double totalIncome = this.calculateIncome(u);
		double totalExpense = this.calculateExpense(u);
		Budget budget = new Budget(totalIncome, totalExpense);
		return budget;
	}

	public List<Double> getStatisticsTotal(User user) throws BudgetException {
		if (user.getAccounts().isEmpty()) {
			throw new BudgetException("No accounts for this user, buget can't be calculated!");
		}
		List<Double> statisticTotal = new ArrayList<Double>();
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(ALL_TRANSACTIONS_TRANS_FOR_USER,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, user.getId());
			ResultSet rs = pstmt.executeQuery();
			double totalIncome = 0, totalOutcome = 0;
			while (rs.next()) {
				if (rs.getInt("t.is_income") == 1) {
					totalIncome += rs.getDouble("t.amount");
				} else {
					totalOutcome += rs.getDouble("t.amount");
				}
			}
			statisticTotal.add(totalIncome);
			statisticTotal.add(totalOutcome);
			double incomeVsOutcome = totalIncome - totalOutcome;
			statisticTotal.add(incomeVsOutcome);
			return statisticTotal;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new BudgetException("No transactions for this user!", e);
		}
	}

	public Map<String, List<Double>> getStatisticsAllAccounts(User user) throws BudgetException {
		if (user.getAccounts().isEmpty()) {
			throw new BudgetException("No accounts for this user, buget can't be calculated!");
		}
		// List<Integer> incomeVsOutcome=new ArrayList<Integer>(2);
		Map<String, List<Double>> allBudgets = new HashMap<String, List<Double>>();
		// Vzimam akauntite na lognatiq user
		HashSet<Account> userAccounts = new HashSet<>(user.getAccounts());
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(ALL_TRANSACTIONS_TRANS_FOR_USER,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, user.getId());
			ResultSet rs = pstmt.executeQuery();

			for (Account account : userAccounts) {
				List<Double> accountBudget = new ArrayList<Double>();
				double income = 0, outcome = 0;
				rs.beforeFirst();
				while (rs.next()) {
					if ((rs.getInt("t.accounts_id") == account.getAccount_id()) && (rs.getInt("t.is_income") == 1)) {
						income += rs.getDouble("t.amount");
					}
					if ((rs.getInt("t.accounts_id") == account.getAccount_id()) && (rs.getInt("t.is_income") == 0)) {
						outcome += rs.getDouble("t.amount");
					}
					// incomeOutcome.add(rs.getDouble(1));
				}
				accountBudget.add(income);
				accountBudget.add(outcome);
				double incomeVsOutcome = income - outcome;
				accountBudget.add(incomeVsOutcome);
				double accountOldBBalance = account.getBalance();
				accountBudget.add(accountOldBBalance);
				accountBudget.add(accountOldBBalance + incomeVsOutcome);
				allBudgets.put(account.getAccountName(), accountBudget);
			}
			return allBudgets;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new BudgetException("No transactions for this user!", e);
		}
	}

//	public Map<Account, List<Transaction>> allTransactionsAllAccounts(User user) throws BudgetException {
//		if (user.getAccounts().isEmpty()) {
//			throw new BudgetException("No accounts for this user, buget can't be calculated!");
//		}
//		// List<Integer> incomeVsOutcome=new ArrayList<Integer>(2);
//		Map<Account, List<Transaction>> result = new HashMap<Account, List<Transaction>>();
//		// Vzimam akauntite na lognatiq user
//		HashSet<Account> userAccounts = new HashSet<>(user.getAccounts());
//		PreparedStatement pstmt;
//		try {
//			pstmt = DBConnection.getInstance().getConnection().prepareStatement(ALL_TRANSACTIONS_TRANS_FOR_USER,
//					Statement.RETURN_GENERATED_KEYS);
//			pstmt.setInt(1, user.getId());
//			ResultSet rs = pstmt.executeQuery();
//
//			for (Account account : userAccounts) {
//				List<Transaction> transactions = new ArrayList<Transaction>();
//				rs.beforeFirst();
//				while (rs.next()) {
//					if (rs.getInt("t.accounts_id") == account.getAccount_id()) {
//						boolean isIncome=rs.getInt("t.is_income")==1?true:false;
//						transactions.add(new Transaction(rs.getString("t.`payee/payer`"), rs.getDouble("t.amount"), rs.getTimestamp("t.date_paid").toLocalDateTime(), isIncome));
//					}
//				}
//				result.put(account, transactions);
//			}
//			return result;
//
//		} catch (ClassNotFoundException | SQLException | TransactionException e) {
//			e.printStackTrace();
//			throw new BudgetException("No transactions for this user!", e);
//		}
//	}

}
