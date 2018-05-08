package com.financetracker.model.transactions;

import java.util.List;

import com.financetracker.exceptions.PlannedTransactionException;
import com.financetracker.exceptions.RecurrencyException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.users.User;

public interface IPlannedTransactionDAO {

	List<PlannedTransaction> getAllPlannedTransactions(User user) throws PlannedTransactionException, RecurrencyException;
	
	int addTransaction(PlannedTransaction plannedTransaction) throws PlannedTransactionException;

	void deletePlannedTransaction(int transactionID) throws TransactionException;

	void payPlannedTransaction(PlannedTransaction plannedTransaction)
			throws PlannedTransactionException, TransactionException;


}
