package com.financetracker.model.accounts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.model.accountType.AccountTypeDAO;
import com.financetracker.model.currencies.CurrencyDAO;
import com.financetracker.model.users.User;

@Component
public class AccountDAO {

	private static final String ALL_ACCOUNTS_FOR_USER = "SELECT a.id, a.name, a.balance, a.last_4_digits, a.percentage, a.payment_due_day, c.currency_type, acct.type FROM accounts a join currencies c on (a.currencies_id1=c.id) join account_types acct on (a.account_type_id=acct.id) where a.user_id=?;";
	private static final String CHECK_IF_ACCOUNT_EXISTS = "SELECT * FROM accounts a JOIN users u ON (a.user_id=u.id) WHERE a.name =? and u.id=?";
	private static final String ADD_ACCOUNT_SQL = "insert into accounts (name, balance, last_4_digits, currencies_id1, account_type_id, user_id) values (?, ?, ?, ?, ?, ?);";
	@Autowired
	private CurrencyDAO currencyDAO;
	@Autowired
	private AccountTypeDAO accountTypeDAO;

	public int addNewAccount(Account a, HttpSession session) throws AccountException {
		PreparedStatement pstmt;
		try {
			if (!accountExists(a, session)) {
				// first add the account to DB
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(ADD_ACCOUNT_SQL,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, a.getAccountName());
				pstmt.setDouble(2, a.getBalance());
				// bez account nomer ne raboti!!
				pstmt.setInt(3, Integer.parseInt(a.getLastFourDigits()));
				pstmt.setInt(4, currencyDAO.getCurrencyId(a.getCurrency()));
				pstmt.setInt(5, accountTypeDAO.getAccountTypeId(a.getType()));
				pstmt.setInt(6, ((User)session.getAttribute("user")).getId());

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

	private boolean accountExists(Account a, HttpSession session) throws AccountException {
		if ((session == null) || (session.getAttribute("user") == null)) {
			throw new AccountException();
		}
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getInstance().getConnection().prepareStatement(CHECK_IF_ACCOUNT_EXISTS);
			pstmt.setString(1, a.getAccountName());
			pstmt.setInt(2, ((User) session.getAttribute("user")).getId());
			//pstmt.setInt(1, a.getUser().getId());
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
				allAccounts.add(new Account(rs.getInt("a.id"), user, rs.getString("a.name"), rs.getDouble("a.balance"),
						"" + rs.getInt("a.last_4_digits"), (byte) (rs.getInt("a.percentage")),
						rs.getInt("a.payment_due_day"), rs.getString("c.currency_type"), rs.getString("acct.type")));
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
