package com.financetracker.model.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CategoryException;
import com.financetracker.exceptions.PlannedTransactionException;
import com.financetracker.exceptions.RecurrencyException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.AccountDAO;
import com.financetracker.model.categories.CategoryDAO;
import com.financetracker.model.users.User;
import com.financetracker.recurrencies.RecurrencyDAO;

@Component
public class PlannedTransactionDAO implements IPlannedTransactionDAO{
	
	private static final String ADD_TRANSACTION_SQL = "INSERT INTO transactions VALUES (null,?,?,?,?,?,?,?,false)";
	private static final String ADD_PLANNED_TRANSACTION_SQL = "INSERT INTO planed_transactions VALUES(null,?,?)";
	private static final String GET_ALL_PLANNED_TRANSACTIONS_SQL = "SELECT pt.id,t.`payee/payer`,t.amount,pt.planed_date,t.accounts_id,t.categories_id,t.is_income,pt.recurencies_id FROM planed_transactions pt JOIN transactions t ON pt.id = t.planed_transactions_id JOIN accounts ON t.accounts_id = accounts.id JOIN users ON accounts.user_id = users.id WHERE users.id = ? AND t.is_paid = false;";
	private static final String REMOVE_PLANNED_TRANSACTION_SQL = "DELETE FROM planed_transactions WHERE planed_transactions.id=?";
	private static final String FIND_TRANSACTION_TO_DELETE_SQL = "SELECT transactions.id FROM transactions WHERE transactions.planed_transactions_id = ?";
	private static final String ALL_USERS_PLANNED_TRANSACTIONS_SQL = "select users.first_name,pt.id,t.`payee/payer`,t.amount,pt.planed_date,t.accounts_id,t.categories_id,t.is_income,pt.recurencies_id FROM planed_transactions pt JOIN transactions t ON pt.id = t.planed_transactions_id JOIN accounts ON t.accounts_id = accounts.id JOIN users ON accounts.user_id = users.id WHERE t.is_paid = false";
	private static final String UPDATE_PLANNED_TRANSACTION_DATE_SQL = "UPDATE planed_transactions pt SET pt.planed_date = ? WHERE pt.id = ?";

	@Autowired
	private DBConnection DBConnection;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private RecurrencyDAO recurrencyDAO;
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Override
	public List<PlannedTransaction> getAllPlannedTransactions(User user) throws PlannedTransactionException{
		if(user == null) {
			throw new PlannedTransactionException("This user doesn't exist, please try again!");
		}
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_ALL_PLANNED_TRANSACTIONS_SQL);
			statement.setInt(1, user.getId());
			List<PlannedTransaction> allPlannedTransactions = new LinkedList<PlannedTransaction>();
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				allPlannedTransactions.add(new PlannedTransaction(resultSet.getInt("pt.id"), resultSet.getString("payee/payer"),
						resultSet.getDouble("t.amount"),
						resultSet.getTimestamp("pt.planed_date").toLocalDateTime().toLocalDate(),
						accountDAO.getAccountById(resultSet.getInt("t.accounts_id")),
						categoryDAO.getCategoryNameById(resultSet.getInt("t.categories_id")),
						resultSet.getBoolean("t.is_income"),recurrencyDAO.getRecurrencyNameById((resultSet.getInt("pt.recurencies_id")))));
			}
			return allPlannedTransactions;
		} catch (SQLException | TransactionException | AccountException | CategoryException | RecurrencyException e) {
			e.printStackTrace();
			throw new PlannedTransactionException("Cannot get transactions, please try again later!", e);
		}
	}
	
	@Override
	public List<PlannedTransaction> getAllUsersPlannedTransactions() throws PlannedTransactionException{
		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(ALL_USERS_PLANNED_TRANSACTIONS_SQL);
			List<PlannedTransaction> allUsersPlannedTransactions = new LinkedList<PlannedTransaction>();

			while(resultSet.next()) {
				allUsersPlannedTransactions.add(new PlannedTransaction(resultSet.getInt("pt.id"), resultSet.getString("payee/payer"),
						resultSet.getDouble("t.amount"),resultSet.getTimestamp("pt.planed_date").toLocalDateTime().toLocalDate(),
						accountDAO.getAccountById(resultSet.getInt("t.accounts_id")),categoryDAO.getCategoryNameById(resultSet.getInt("t.categories_id")),
						resultSet.getBoolean("t.is_income"),recurrencyDAO.getRecurrencyNameById((resultSet.getInt("pt.recurencies_id")))));
			}
			return allUsersPlannedTransactions;
		} catch (SQLException | TransactionException | AccountException | CategoryException | RecurrencyException e) {
			e.printStackTrace();
			throw new PlannedTransactionException("Cannot get transactions, please try again later!", e);
		}
	}
	
	@Override
	public int addTransaction(PlannedTransaction plannedTransaction) throws PlannedTransactionException {
		if(plannedTransaction == null) {
			throw new PlannedTransactionException("Entered planned transaction is empty, please try again!");
		}
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
			connection.setAutoCommit(false);
			
			PreparedStatement statement = connection.prepareStatement(ADD_PLANNED_TRANSACTION_SQL, Statement.RETURN_GENERATED_KEYS);
			statement.setTimestamp(1, Timestamp.valueOf(plannedTransaction.getPlannedDate().atStartOfDay()));
			statement.setInt(2, recurrencyDAO.getRecurrencyIdByName(plannedTransaction.getRecurrency()));
			statement.executeUpdate();
			ResultSet resultSetPlannedTransaction = statement.getGeneratedKeys();
			resultSetPlannedTransaction.next();
			connection.commit();
			
			int plannedTransactionId = resultSetPlannedTransaction.getInt(1);
			
			PreparedStatement pstmt = connection.prepareStatement(ADD_TRANSACTION_SQL, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, plannedTransaction.getPayee());
			pstmt.setDouble(2, plannedTransaction.getAmount());
			pstmt.setTimestamp(3, Timestamp.valueOf(plannedTransaction.getPlannedDate().atStartOfDay()));
			pstmt.setInt(4, plannedTransaction.getAccount().getAccount_id());
			pstmt.setInt(5, categoryDAO.getCategoryIdByName(plannedTransaction.getCategory()));
			pstmt.setBoolean(6, plannedTransaction.getIsIncome());
			pstmt.setInt(7, plannedTransactionId);
			pstmt.executeUpdate();
			ResultSet resultSetTransaction = pstmt.getGeneratedKeys();
			resultSetTransaction.next();
			return resultSetTransaction.getInt(1);

		} catch (SQLException | CategoryException | RecurrencyException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new PlannedTransactionException("Unable to rollback the data!", e1);
			}
			throw new PlannedTransactionException("Something went wrong, please try again!", e);
		}finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new PlannedTransactionException("Something went wrong, couldn't add the planned transaction!", e);
			}
		}
	}
	
	@Override
	public void deletePlannedTransaction(int plannedTransactionID) throws PlannedTransactionException {
		if(plannedTransactionID <= 0) {
			throw new PlannedTransactionException("Entered planned transaction is empty, please try again!");
		}
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(REMOVE_PLANNED_TRANSACTION_SQL);
			pstmt.setInt(1, plannedTransactionID);
			pstmt.executeUpdate();
			PreparedStatement statement = connection.prepareStatement(FIND_TRANSACTION_TO_DELETE_SQL);
			statement.setInt(1, plannedTransactionID);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int transactionID = resultSet.getInt(1);
			transactionDAO.deleteTransaction(transactionID);
			connection.commit();
		} catch (SQLException | TransactionException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new PlannedTransactionException("Cannot delete planned transaction, please try again!", e);
		}finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void payPlannedTransaction(PlannedTransaction plannedTransaction) throws PlannedTransactionException {
		if(plannedTransaction == null) {
			throw new PlannedTransactionException("Entered planned transaction is empty, please try again!");
		}
		try {
			Transaction newTransaction = new Transaction(plannedTransaction.getPayee(), plannedTransaction.getAmount(), plannedTransaction.getPlannedDate(),plannedTransaction.getAccount(), plannedTransaction.getCategory(), plannedTransaction.getIsIncome(), plannedTransaction.getId());
			transactionDAO.addTransaction(newTransaction);
		
		} catch (TransactionException e) {
			e.printStackTrace();
			throw new PlannedTransactionException("Something went wrong, cannot pay planned transaction!", e);
		}
	}
	
	@Override
	public void updatePlannedTransactionDate(PlannedTransaction transaction,LocalDate newDate) throws PlannedTransactionException {
		if(transaction == null || newDate == null) {
			throw new PlannedTransactionException("Entered planned transaction is empty, please try again!");
		}
		
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_PLANNED_TRANSACTION_DATE_SQL);
			pstmt.setTimestamp(1, Timestamp.valueOf(newDate.atStartOfDay()));
			pstmt.setInt(2, transaction.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PlannedTransactionException("Something went wrong, cannot pay planned transaction!", e);
		}		
	}


}
