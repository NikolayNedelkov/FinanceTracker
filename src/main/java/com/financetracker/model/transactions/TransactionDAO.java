package com.financetracker.model.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.IAccountDAO;

@Component
public class TransactionDAO implements ITransactionDAO {
	
	private static final String ADD_TRANSACTION_SQL = "INSERT INTO transactions VALUES (null,?,?,?,?,?,?)";
	private static final String REMOVE_TRANSACTION_SQL = "DELETE FROM transactions WHERE transactions.id=?";
	private static final String GET_ALL_TRANSACTIONS_SQL = "SELECT `payee/payer`,amount, date_paid,accounts_id,categories_id,is_income FROM transactions where accounts_id=?";

	@Autowired
	private IAccountDAO accountDAO;

	@Override
	public List<Transaction> getAllTransactions(Account account)
			throws TransactionException, SQLException, AccountException {
		try {
			// TODO:DBConnection should be component with autowired annotation
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_ALL_TRANSACTIONS_SQL);
			statement.setInt(1, account.getAccount_id());
			ResultSet resultSet = statement.executeQuery();
			List<Transaction> allTransactions = new ArrayList<Transaction>();
			while (resultSet.next()) {
				allTransactions.add(new Transaction(resultSet.getString("payee/payer"), resultSet.getDouble("amount"),
						(resultSet.getTimestamp("date_paid").toLocalDateTime().toLocalDate()),
						accountDAO.getAccountById(resultSet.getInt("accounts_id")), resultSet.getInt("categories_id"),
						resultSet.getBoolean("is_income")));
			}
			return allTransactions;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot get transactions, please try again later!", e);
		}

	}

	@Override
	public int addTransaction(Transaction transaction) throws TransactionException, SQLException {
		PreparedStatement pstmt;
		Connection connection = null;

		try {
			connection = DBConnection.getInstance().getConnection();
			connection.setAutoCommit(false);
			pstmt = connection.prepareStatement(ADD_TRANSACTION_SQL, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, transaction.getPayee());
			pstmt.setDouble(2, transaction.getAmount());
			pstmt.setTimestamp(3, Timestamp.valueOf(transaction.getDate().atStartOfDay()));
			pstmt.setInt(4, transaction.getAccount().getAccount_id());
			pstmt.setInt(5, transaction.getCategory());
			pstmt.setInt(6, (transaction.getIsIncome() ? 1 : 0));
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
			return resultSet.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			connection.rollback();
			throw new TransactionException("Something went wrong, please try again!", e);
		} finally {
			connection.setAutoCommit(true);
		}
	}

	@Override
	public int removeTransaction(Transaction transaction) {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getInstance().getConnection().prepareStatement(REMOVE_TRANSACTION_SQL,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, transaction.getId());
			pstmt.executeUpdate();
			ResultSet resultSet = pstmt.getGeneratedKeys();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
