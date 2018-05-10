package com.financetracker.model.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.model.accountType.IAccountTypeDAO;
import com.financetracker.model.currencies.ICurrencyDAO;
import com.financetracker.model.users.User;
import com.financetracker.util.AccountNameComparator;
import com.financetracker.util.AccountTypeComparator;
import com.financetracker.util.BalanceComparator;
import com.financetracker.util.CurrencyComparator;

@Component
public class AccountDAO implements IAccountDAO {

	private static final String UPDATE_ACCOUNT = "update accounts set name=?, balance=?, last_4_digits=?, percentage=?, currencies_id1=?, account_type_id=? where id=?;";
	private static final String GET_ACCOUNT_BY_ID = "SELECT a.id, a.name, a.balance, a.last_4_digits, a.percentage, a.payment_due_day, c.currency_type, acct.type FROM accounts a join currencies c on (a.currencies_id1=c.id) join account_types acct on (a.account_type_id=acct.id) where a.id=?;";
	private static final String GET_ACCOUNT_BY_NAME = "SELECT a.id, a.name, a.balance, a.last_4_digits, a.percentage, a.payment_due_day, c.currency_type, acct.type FROM accounts a join currencies c on (a.currencies_id1=c.id) join account_types acct on (a.account_type_id=acct.id) where a.name like ?;";
	private static final String ALL_ACCOUNTS_FOR_USER = "SELECT a.id, a.name, a.balance, a.last_4_digits, a.percentage, a.payment_due_day, c.currency_type, acct.type FROM accounts a join currencies c on (a.currencies_id1=c.id) join account_types acct on (a.account_type_id=acct.id) where a.user_id=? and (a.is_deleted is null or a.is_deleted=0);";
	private static final String CHECK_IF_ACCOUNT_EXISTS = "SELECT * FROM accounts a JOIN users u ON (a.user_id=u.id) WHERE a.name =? and u.id=?";
	private static final String ADD_ACCOUNT_SQL = "insert into accounts (name, balance, last_4_digits, currencies_id1, account_type_id, user_id) values (?, ?, ?, ?, ?, ?);";
	private static final String DELETE_ACCOUNT = "update accounts set is_deleted=1 where id=?;";

	@Autowired
	private ICurrencyDAO currencyDAO;
	@Autowired
	private IAccountTypeDAO accountTypeDAO;
	@Autowired
	private DBConnection DBConnection;

	@Override
	public int addNewAccount(Account a, HttpSession session) throws AccountException {
		PreparedStatement pstmt;
		try {
			if (!accountExists(a, session)) {
				// first add the account to DB
				pstmt = DBConnection.getConnection().prepareStatement(ADD_ACCOUNT_SQL, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, a.getAccountName());
				pstmt.setDouble(2, a.getBalance());
				// bez account nomer ne raboti!!
				if ((a.getLastFourDigits() == null) || (a.getLastFourDigits().trim().length() < 3)) {
					pstmt.setNull(3, java.sql.Types.INTEGER);
				} else {
					pstmt.setInt(3, Integer.parseInt(a.getLastFourDigits()));
				}
				pstmt.setInt(4, currencyDAO.getCurrencyId(a.getCurrency()));
				pstmt.setInt(5, accountTypeDAO.getAccountTypeId(a.getType()));
				pstmt.setInt(6, ((User) session.getAttribute("user")).getId());

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
			pstmt = DBConnection.getConnection().prepareStatement(CHECK_IF_ACCOUNT_EXISTS);
			pstmt.setString(1, a.getAccountName());
			pstmt.setInt(2, ((User) session.getAttribute("user")).getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccountException();
		}
	}

	// @Override
	public Set<Account> getAllAccountsForUser(User user) throws AccountException {
		Comparator<Account> comparator = new AccountTypeComparator();
		Set<Account> allAccounts = new TreeSet<Account>(comparator);
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(ALL_ACCOUNTS_FOR_USER);
			pstmt.setInt(1, user.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				allAccounts.add(new Account(rs.getInt("a.id"), user, rs.getString("a.name"), rs.getDouble("a.balance"),
						"" + rs.getInt("a.last_4_digits"), (byte) (rs.getInt("a.percentage")),
						rs.getInt("a.payment_due_day"), rs.getString("c.currency_type"), rs.getString("acct.type")));
			}
			return allAccounts;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccountException("Account can't be added, database issue", e);
		}
	}

	public Set<Account> getSortedAccounts(User user, String criteria) throws AccountException {
		Set<Account> accounts = getAllAccountsForUser(user);
		Comparator<Account> comparator;
		if ((criteria == null) || (criteria.trim().length() == 0)) {
			return accounts;
		}
		switch (criteria) {
		case "Balance":
			comparator = new BalanceComparator();
			break;
		case "Currency":
			comparator = new CurrencyComparator();
			break;
		case "Account type":
			comparator = new AccountTypeComparator();
			break;
		default:
			comparator = new AccountTypeComparator();
			break;
		}
		Set<Account> resultSet = new TreeSet<Account>(comparator);
		for (Account account : accounts) {
			resultSet.add(account);
		}
		return resultSet;
	}

	// Validaciq posle
	@Override
	public Account getAccountById(int id) throws AccountException {
		try {
			PreparedStatement pst = DBConnection.getConnection().prepareStatement(GET_ACCOUNT_BY_ID,
					Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Account account = new Account(rs.getInt("a.id"), rs.getString("a.name"), rs.getDouble("a.balance"),
						"" + rs.getInt("a.last_4_digits"), (byte) (rs.getInt("a.percentage")),
						rs.getInt("a.payment_due_day"), rs.getString("c.currency_type"), rs.getString("acct.type"));
				return account;
			} else
				throw new AccountException("Couldn't retrieve an account with id " + id);
		} catch (SQLException e) {
			throw new AccountException("DB-issue!");
		}
	}

	@Override
	public Account getAccountByName(String name) throws AccountException {
		try {
			PreparedStatement pst = DBConnection.getConnection().prepareStatement(GET_ACCOUNT_BY_NAME,
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				Account account = new Account(rs.getInt("a.id"), rs.getString("a.name"), rs.getDouble("a.balance"),
						"" + rs.getInt("a.last_4_digits"), (byte) (rs.getInt("a.percentage")),
						rs.getInt("a.payment_due_day"), rs.getString("c.currency_type"), rs.getString("acct.type"));
				return account;
			} else
				throw new AccountException("Couldn't retrieve an account with name: " + name);
		} catch (SQLException e) {
			throw new AccountException("DB-issue!");
		}
	}

	@Override
	public void updateAccount(Account updated) throws AccountException {
		try {
			Account notUpdated = this.getAccountById(updated.getAccount_id());

			Connection connection = DBConnection.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pst = connection.prepareStatement(UPDATE_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, updated.getAccountName());
			pst.setDouble(2, updated.getBalance());
			pst.setInt(3, Integer.parseInt(updated.getLastFourDigits()));
			Float percentage = updated.getPercentage();
			if ((percentage == null) || ((!updated.getType().toLowerCase().equals("loan"))
					&& (!updated.getType().toLowerCase().equals("credit card")))) {
				pst.setNull(4, java.sql.Types.DOUBLE);
			} else {
				pst.setDouble(4, updated.getPercentage());
			}
			pst.setInt(5, currencyDAO.getCurrencyId(updated.getCurrency()));
			pst.setInt(6, accountTypeDAO.getAccountTypeId(updated.getType()));
			pst.setInt(7, notUpdated.getAccount_id());

			pst.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (AccountException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new AccountException("Could not update the account");
		}
	}

	public void deleteAccount(int id) {
		PreparedStatement pst;
		try {
			pst = DBConnection.getConnection().prepareStatement(DELETE_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, id);
			pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<Account> getAccountsBySort(User user, String sort) throws AccountException {
		Set<Account> accounts = getAllAccountsForUser(user);
		Set<Account> result = new HashSet<Account>();
		switch (sort) {
		case "debit":
			accounts.stream()
					.filter(acc -> acc.getType().toLowerCase().equals("cash") || acc.getType().toLowerCase().equals("paypal")|| acc.getType().toLowerCase().equals("gift card"))
					.forEach(acc -> result.add(acc));
			break;
		case "investment":
			accounts.stream().filter(
					acc -> acc.getType().toLowerCase().equals("pension") || acc.getType().toLowerCase().equals("ira") || acc.getType().toLowerCase().equals("other investment"))
					.forEach(acc -> result.add(acc));
			break;
		default:
			accounts.stream().filter(acc -> acc.getType().toLowerCase().equals("creditcard") || acc.getType().toLowerCase().equals("loan"))
					.forEach(acc -> result.add(acc));
			break;
		}
		return result;
	}
}
