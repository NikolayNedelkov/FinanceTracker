package com.financetracker.model.transactions;

import java.sql.SQLException;
import java.util.List;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.users.User;

public interface ITransactionDAO {

	int addTransaction(Transaction transaction) throws TransactionException, SQLException;

	void deleteTransaction(int transactionID) throws TransactionException;

	//int makePlannedTransaction(Transaction transaction) throws TransactionException;

	List<Transaction> getAllTransactions(User user) throws TransactionException, AccountException;


}