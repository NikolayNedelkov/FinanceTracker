package com.financetracker.model.transactions;

import java.util.List;

import com.financetracker.exceptions.PlannedTransactionException;
import com.financetracker.model.users.User;

public interface IPlannedTransactionDAO {

	List<PlannedTransaction> getAllPlannedTransactions(User user) throws PlannedTransactionException;
	
	int addTransaction(PlannedTransaction plannedTransaction) throws PlannedTransactionException;

	void deletePlannedTransaction(int transactionID) throws PlannedTransactionException;

	void payPlannedTransaction(PlannedTransaction plannedTransaction) throws PlannedTransactionException;

	List<PlannedTransaction> getAllUsersPlannedTransactions() throws PlannedTransactionException;


}
