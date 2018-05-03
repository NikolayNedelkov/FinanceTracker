package com.financetracker.model.transactions;

import java.sql.SQLException;
import java.util.List;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;

public interface ITransactionDAO {

	List<Transaction> getAllTransactions(Account account) throws TransactionException, SQLException, AccountException;

	int addTransaction(Transaction transaction) throws TransactionException, SQLException;

	int removeTransaction(Transaction transaction);

}