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
import com.financetracker.exceptions.CategoryException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.accounts.IAccountDAO;
import com.financetracker.model.categories.ICategoryDAO;

@Component
public class TransactionDAO implements ITransactionDAO {

	private static final String ADD_TRANSACTION_SQL = "INSERT INTO transactions VALUES (null,?,?,?,?,?,?,0,true)";
	private static final String REMOVE_TRANSACTION_SQL = "DELETE FROM transactions WHERE transactions.id=?";
	private static final String GET_ALL_TRANSACTIONS_SQL = "SELECT id,`payee/payer`,amount, date_paid,accounts_id,categories_id,is_income FROM transactions where accounts_id=? AND is_paid = true";

	@Autowired
	private IAccountDAO accountDAO;

	@Autowired
	private ICategoryDAO categoryDAO;

	@Override
	public List<Transaction> getAllTransactions(Account account) throws TransactionException, AccountException {
		try {
			// TODO:DBConnection should be component with autowired annotation
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_ALL_TRANSACTIONS_SQL);
			statement.setInt(1, account.getAccount_id());
			ResultSet resultSet = statement.executeQuery();
			List<Transaction> allTransactions = new ArrayList<Transaction>();
			while (resultSet.next()) {
				allTransactions.add(new Transaction(resultSet.getInt("id"), resultSet.getString("payee/payer"),
						resultSet.getDouble("amount"),
						(resultSet.getTimestamp("date_paid").toLocalDateTime().toLocalDate()),
						accountDAO.getAccountById(resultSet.getInt("accounts_id")),
						categoryDAO.getCategoryNameById(resultSet.getInt("categories_id")),
						resultSet.getBoolean("is_income")));
			}
			return allTransactions;
		} catch (ClassNotFoundException | CategoryException | SQLException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot get transactions, please try again later!", e);
		}

	}

	@Override
	public int addTransaction(Transaction transaction) throws TransactionException {
		Connection connection = null;
			try {
				connection = DBConnection.getInstance().getConnection();
				connection.setAutoCommit(false);
				PreparedStatement pstmt = connection.prepareStatement(ADD_TRANSACTION_SQL,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, transaction.getPayee());
				pstmt.setDouble(2, transaction.getAmount());
				pstmt.setTimestamp(3, Timestamp.valueOf(transaction.getDate().atStartOfDay()));
				pstmt.setInt(4, transaction.getAccount().getAccount_id());
				pstmt.setInt(5, categoryDAO.getCategoryIdByName(transaction.getCategory()));
				pstmt.setBoolean(6, transaction.getIsIncome());
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
			} catch (SQLException | CategoryException | ClassNotFoundException e) {
				e.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				throw new TransactionException("Something went wrong, please try again!", e);
			} finally {
				try {
					connection.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	@Override
	public void deleteTransaction(int transactionID) throws TransactionException {
		try {
			PreparedStatement pstmt = DBConnection.getInstance().getConnection().prepareStatement(REMOVE_TRANSACTION_SQL);
			pstmt.setInt(1, transactionID);
			pstmt.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot delete transaction, please try again!", e);
		}
	}
}
