package com.financetracker.model.accounts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;

public class AccountDAO {
	private static final String CHECK_IF_ACCOUNT_EXISTS = "SELECT * FROM accounts where name =?";
	private static final String ADD_ACCOUNT_SQL = "insert into accounts (name, balance, currencies_id1, account_type_id, user_id) values (?, ?, ?, ?,";
	private static AccountDAO instance = null;

	private AccountDAO() {

	}

	public static AccountDAO getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new AccountDAO();
		}
		return instance;
	}

	public boolean addNewAccount(Account a) throws AccountException {
		PreparedStatement pstmt;
		try {
			if (!accountExists(a)) {
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(
						ADD_ACCOUNT_SQL + a.getUser().getId() + ");", Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, a.getAccountName());
				pstmt.setDouble(2, a.getBalance());
				pstmt.setString(3, a.getCurrency());
				pstmt.setString(4, a.getType());

				pstmt.executeUpdate();

				ResultSet resultSet = pstmt.getGeneratedKeys();
				resultSet.next();

				return true;
			} else {
				throw new AccountException("You have already added account with name " + a.getAccountName());
			}

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new AccountException("Account already exists!", e);
		}
	}

	private boolean accountExists(Account a) throws AccountException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getInstance().getConnection().prepareStatement(CHECK_IF_ACCOUNT_EXISTS);
			pstmt.setString(1, a.getAccountName());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				// return false;
				return true;
			} else {
				// return true;
				return false;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new AccountException();
		}
	}

	// public boolean deleteAccount(Account a) {
	//
	// }
}
