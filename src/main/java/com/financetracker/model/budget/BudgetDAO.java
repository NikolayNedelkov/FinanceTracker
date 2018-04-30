package com.financetracker.model.budget;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.users.User;

public class BudgetDAO {

	private static final String CALCULATE_INCOME = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '1'";
	private static final String CALCULATE_EXPENSE = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '0'";
	private static BudgetDAO budgetDAO = null;

	private BudgetDAO() {
	}

	public static BudgetDAO getInstance() throws SQLException, ClassNotFoundException {
		if (budgetDAO == null) {
			budgetDAO = new BudgetDAO();
		}
		return budgetDAO;
	}

	public double calculateIncome(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (user != null) {
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(CALCULATE_INCOME);
				pstmt.setInt(1, user.getId());
				pstmt.executeUpdate();

				ResultSet resultSet = pstmt.getGeneratedKeys();
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
				pstmt.executeUpdate();

				ResultSet resultSet = pstmt.getGeneratedKeys();
				resultSet.next();

				return resultSet.getInt(1);
			} else {
				throw new UserException("You account budget cannot be calculated ");
			}

		} catch (SQLException | ClassNotFoundException e) {
			throw new UserException("You account budget cannot be calculated ", e);
		}
	}
	
}
