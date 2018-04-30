package com.financetracker.model.accounts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.model.users.User;

@Component
public class AccountDAO {

	private static final String ALL_ACCOUNTS_FOR_USER = "SELECT id, name, balance, last_4_digits, percentage, payment_due_day, currencies_id1, account_type_id FROM financetracker.accounts where user_id=?;";
	private static final String CHECK_IF_ACCOUNT_EXISTS = "SELECT * FROM accounts a JOIN users u ON (a.user_id=u.id) WHERE a.name =? and u.id=?";
	private static final String ADD_ACCOUNT_SQL = "insert into accounts (name, balance, last_4_digits, currencies_id1, account_type_id, user_id) values (?, ?, ?, ?, ?, ?);";
	private static AccountDAO instance = null;

	private AccountDAO() {
	}

	public static AccountDAO getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new AccountDAO();
		}
		return instance;
	}

	public int addNewAccount(Account a) throws AccountException {
		PreparedStatement pstmt;
		try {
			if (!accountExists(a)) {
				// first add the account to DB
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(ADD_ACCOUNT_SQL,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, a.getAccountName());
				pstmt.setDouble(2, a.getBalance());
				// bez account nomer ne raboti!!
				pstmt.setInt(3, Integer.parseInt(a.getLastFourDigits()));
				pstmt.setInt(4, a.getCurrency());
				pstmt.setInt(5, a.getType());
				pstmt.setInt(6, a.getUser().getId());

				pstmt.executeUpdate();

				ResultSet resultSet = pstmt.getGeneratedKeys();
				resultSet.next();

				return resultSet.getInt(1);
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
			pstmt.setInt(1, a.getUser().getId());
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

	public HashSet<Account> getAllAccountsForUser(User user) throws AccountException {
		HashSet<Account> allAccounts = new HashSet<Account>();
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getInstance().getConnection().prepareStatement(ALL_ACCOUNTS_FOR_USER);
			pstmt.setInt(1, user.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				allAccounts.add(new Account(rs.getInt("id"), user, rs.getString("name"), rs.getDouble("balance"),
						"" + rs.getInt("last_4_digits"), (byte) (rs.getInt("percentage")), rs.getInt("payment_due_day"),
						rs.getInt("currencies_id1"), rs.getInt("account_type_id")));
			}
			return allAccounts;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new AccountException("Account can't be added, database issue", e);
		}
	}
	// public boolean deleteAccount(Account a) {
	// (rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("balance"), balance,
	// currency, type));
	// }
}
