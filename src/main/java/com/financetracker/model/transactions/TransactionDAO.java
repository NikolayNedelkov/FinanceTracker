package com.financetracker.model.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CategoryException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.IAccountDAO;
import com.financetracker.model.categories.ICategoryDAO;
import com.financetracker.model.users.User;

@Component
public class TransactionDAO implements ITransactionDAO {

	private static final String ADD_TRANSACTION_SQL = "INSERT INTO transactions VALUES (null,?,?,?,?,?,?,?,true)";
	private static final String REMOVE_TRANSACTION_SQL = "DELETE FROM transactions WHERE transactions.id=?";
	private static final String GET_ALL_TRANSACTIONS_SQL = "SELECT t.id,t.`payee/payer`,t.amount, t.date_paid,t.accounts_id,t.categories_id,t.is_income FROM transactions t JOIN accounts a ON t.accounts_id = a.id JOIN users u ON a.user_id = u.id WHERE u.id = ? AND t.is_paid = true ORDER BY date_paid DESC";
	private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE accounts SET balance=balance + ? WHERE id = ?";
	
	@Autowired
	DBConnection DBConnection;
	
	@Autowired
	private IAccountDAO accountDAO;

	@Autowired
	private ICategoryDAO categoryDAO;

	@Override
	public List<Transaction> getAllTransactions(User user) throws TransactionException, AccountException {
		try {
			PreparedStatement statement = DBConnection.getConnection().prepareStatement(GET_ALL_TRANSACTIONS_SQL);
			statement.setInt(1, user.getId());
			ResultSet resultSet = statement.executeQuery();
			List<Transaction> allTransactions = new LinkedList<Transaction>();
			while (resultSet.next()) {
				allTransactions.add(new Transaction(resultSet.getInt("id"), resultSet.getString("payee/payer"),
						resultSet.getDouble("amount"),
						(resultSet.getTimestamp("date_paid").toLocalDateTime().toLocalDate()),
						accountDAO.getAccountById(resultSet.getInt("accounts_id")),
						categoryDAO.getCategoryNameById(resultSet.getInt("categories_id")),
						resultSet.getBoolean("is_income")));
			}
			return allTransactions;
		} catch (CategoryException | SQLException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot get transactions, please try again later!", e);
		}

	}

	@Override
	public int addTransaction(Transaction transaction) throws TransactionException {
		Connection connection = null;
			try {
				connection = DBConnection.getConnection();
				connection.setAutoCommit(false);
				PreparedStatement pstmt = connection.prepareStatement(ADD_TRANSACTION_SQL,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, transaction.getPayee());
				pstmt.setDouble(2, transaction.getAmount());
				pstmt.setTimestamp(3, Timestamp.valueOf(transaction.getDate().atStartOfDay()));
				pstmt.setInt(4, transaction.getAccount().getAccount_id());
				pstmt.setInt(5, categoryDAO.getCategoryIdByName(transaction.getCategory()));
				pstmt.setBoolean(6, transaction.getIsIncome());
				pstmt.setInt(7, transaction.getPlannedTransactionId());
				pstmt.executeUpdate();
				ResultSet resultSet = pstmt.getGeneratedKeys();
				resultSet.next();
				PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE);
				double amount = transaction.getAmount();
				if (!transaction.getIsIncome()) {
					amount *= -1;
				}
				statement.setDouble(1, amount);
				statement.setInt(2, transaction.getAccount().getAccount_id());
				statement.executeUpdate();
				connection.commit();
				return resultSet.getInt(1);
			} catch (SQLException | CategoryException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw new TransactionException("Something went wrong, please try again!", e);
			} finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	}

	@Override
	public void deleteTransaction(int transactionID) throws TransactionException {
		try {
			PreparedStatement pstmt = DBConnection.getConnection().prepareStatement(REMOVE_TRANSACTION_SQL);
			pstmt.setInt(1, transactionID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot delete transaction, please try again!", e);
		}
	}

	/*@Override
	public int makePlannedTransaction(Transaction transaction) throws TransactionException {
		Connection connection = null;
			try {
				connection = DBConnection.getConnection();
				connection.setAutoCommit(false);
				PreparedStatement pstmt = connection.prepareStatement(ADD_TRANSACTION_SQL,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, transaction.getPayee());
				pstmt.setDouble(2, transaction.getAmount());
				pstmt.setTimestamp(3, Timestamp.valueOf(transaction.getDate().atStartOfDay()));
				pstmt.setInt(4, transaction.getAccount().getAccount_id());
				pstmt.setInt(5, categoryDAO.getCategoryIdByName(transaction.getCategory()));
				pstmt.setBoolean(6, transaction.getIsIncome());
				pstmt.setInt(7, transaction.getPlannedTransactionId());
				pstmt.executeUpdate();
				ResultSet resultSet = pstmt.getGeneratedKeys();
				resultSet.next();
				Statement statement = connection.createStatement();
				double amount = transaction.getAmount();
				if (!transaction.getIsIncome()) {
					amount *= -1;
				}
				statement.executeUpdate("UPDATE accounts SET balance=balance + " + amount + "WHERE id="
						+ transaction.getAccount().getAccount_id());
				connection.commit();
				return resultSet.getInt(1);
			} catch (SQLException | CategoryException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				throw new TransactionException("Something went wrong, please try again!", e);
			} finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	}*/
}
