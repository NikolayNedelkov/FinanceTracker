package com.financetracker.model.budget;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.users.User;


@Component
public class BudgetDAO {

	private static final String CALCULATE_INCOME = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '1'";
	private static final String CALCULATE_EXPENSE = "SELECT SUM(t.amount) FROM users u JOIN accounts a ON (u.id=a.user_id) JOIN transactions t on (a.id=t.accounts_id) WHERE u.id=? and t.is_income = '0'";


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
		double totalExpense=this.calculateExpense(u);
		Budget budget = new Budget(totalIncome, totalExpense);
		return budget;
	}
	
}
