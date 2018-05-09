package com.financetracker.model.transactions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.exceptions.PlannedTransactionException;
import com.financetracker.exceptions.PlannedTransactionThreadException;
import com.financetracker.exceptions.RecurrencyException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.users.User;


public class PlannedTransactionThread implements Runnable {

	private User user;
	
	@Autowired
	private PlannedTransactionDAO plannedTransactionDAO;	
	
	
	public PlannedTransactionThread(User user) {
		if(user != null) {
			this.user = user;
		}
	}
	
	@Override
	public void run() {
		while(true) {
		
			try {
				List<PlannedTransaction> allTransactions = plannedTransactionDAO.getAllPlannedTransactions(user);
				for(PlannedTransaction transaction: allTransactions) {
						String recurrency = transaction.getRecurrency();
						LocalDate transactionDate = transaction.getDate();
						switch (recurrency.toLowerCase()) {
						case "monthly":
							transactionDate.plusMonths(1);
							break;
                        case "weekly":
                        	transactionDate.plusWeeks(1);
							break;
                        case "daily":
                        	transactionDate.plusDays(1);
							break;
						default:
							break;
						}
						LocalDate currentDate = LocalDateTime.now().toLocalDate();
						if(currentDate.isEqual(transactionDate)) {
							///redirect kym stranica da te pita dali iskash da plati smetkata
							plannedTransactionDAO.payPlannedTransaction(transaction);
						}
					}
				} catch (PlannedTransactionException | RecurrencyException | TransactionException e) {
				e.printStackTrace();
				
					//throw new PlannedTransactionThreadException("Oops, something went wrong!",e);
				
			}
		}
		
	}
}
