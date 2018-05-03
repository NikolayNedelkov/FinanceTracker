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

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.BudgetException;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.users.User;

@Component
public class BudgetDAO {

	private static final String CALCULATE_INCOME = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '1'";
	private static final String CALCULATE_EXPENSE = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '0'";
	private static final String ALL_INCOMES_TRANS_FOR_ACCOUNT = "select sum(t.amount) from users u join accounts a on(u.id=a.user_id) join transactions t on (a.id=t.accounts_id) where u.id=? and a.id=? and t.is_income='1'; ";
	private static final String ALL_OUTCOMES_TRANS_FOR_ACCOUNT = "select sum(t.amount) from users u join accounts a on(u.id=a.user_id) join transactions t on (a.id=t.accounts_id) where u.id=? and a.id=? and t.is_income='0'; ";

	public double calculateIncome(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (user != null) {
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(CALCULATE_INCOME);
				pstmt.setInt(1, user.getId());

				ResultSet resultSet = pstmt.executeQuery();
				resultSet.next();

				return resultSet.getInt(1);
			} else {
				throw new UserException("You account budget cannot be calculated ");
			}

		} catch (SQLException | ClassNotFoundException e) {
			throw new UserException("You account budget cannot be calculated ", e);
		}
	}

	public double calculateExpense(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (user != null) {
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(CALCULATE_EXPENSE);
				pstmt.setInt(1, user.getId());

				ResultSet resultSet = pstmt.executeQuery();
				resultSet.next();

				return resultSet.getInt(1);
			} else {
				throw new UserException("You account budget cannot be calculated ");
			}

		} catch (SQLException | ClassNotFoundException e) {
			throw new UserException("You account budget cannot be calculated ", e);
		}
	}

	public Budget getBudget(User u) throws UserException {
		double totalIncome = this.calculateIncome(u);
		double totalExpense = this.calculateExpense(u);
		Budget budget = new Budget(totalIncome, totalExpense);
		return budget;
	}

	public Map<String, List<Double>> getBudgetByAccount(User user) throws BudgetException {
		if (user.getAccounts().isEmpty()) {
			throw new BudgetException("No accounts for this user, buget can't be calculated!");
		}
		// List<Integer> incomeVsOutcome=new ArrayList<Integer>(2);
		Map<String, List<Double>> allBudgets = new HashMap<String, List<Double>>();
		// Vzimam akauntite na lognatiq user
		HashSet<Account> userAccounts = new HashSet<>(user.getAccounts());

		for (Account account : userAccounts) {
			List<Double> accountBudget = inOutForAccount(account.getAccount_id(), user);
			allBudgets.put(account.getAccountName(), accountBudget);
		}
		return allBudgets;

	}

	private List<Double> inOutForAccount(int accId, User user) throws BudgetException {
		List<Double> incomeOutcome = new ArrayList<Double>(2);

		try {
			// Get all income transactions for user and account
			PreparedStatement pstmt1 = DBConnection.getInstance().getConnection()
					.prepareStatement(ALL_INCOMES_TRANS_FOR_ACCOUNT, Statement.RETURN_GENERATED_KEYS);

			pstmt1.setInt(1, user.getId());
			pstmt1.setInt(2, accId);
			//pstmt1.executeQuery();
			ResultSet rs = pstmt1.executeQuery();
			// if any transactions found, add them to the list for this account
			if (rs.next()) {
				incomeOutcome.add(rs.getDouble(1));
			} else {
				// if not, add 0
				incomeOutcome.add(0.0);
			}

			// Get all outcome transactions for user and account
			PreparedStatement pstmt2 = DBConnection.getInstance().getConnection()
					.prepareStatement(ALL_OUTCOMES_TRANS_FOR_ACCOUNT, Statement.RETURN_GENERATED_KEYS);

			pstmt2.setInt(1, user.getId());
			pstmt2.setInt(2, accId);
			//pstmt2.executeQuery();
			ResultSet rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				incomeOutcome.add(rs2.getDouble(1));
			} else {
				incomeOutcome.add(0.0);
			}
			return incomeOutcome;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new BudgetException("No + or - transaction for this account!", e);
		}
	}

}
