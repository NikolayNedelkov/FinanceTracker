package com.financetracker.model.transactions;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.exceptions.PlannedTransactionException;

//@Component
public class PlannedTransactionThread extends Thread {

	@Autowired
	private PlannedTransactionDAO plannedTransactionDAO;

	public PlannedTransactionThread() {
		 Thread.currentThread().setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				/// eto tuka
			}
			try {
				List<PlannedTransaction>allTransactions = plannedTransactionDAO.getAllUsersPlannedTransactions();
				System.out.println(plannedTransactionDAO);
				for (PlannedTransaction transaction : allTransactions) {
					System.out.println(transaction);
					
					String recurrency = transaction.getRecurrency();
					System.out.println(recurrency);
					
					LocalDate transactionDate = transaction.getDate();
					System.out.println(transactionDate);
					
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
					System.out.println("new date" + transactionDate);
					LocalDate currentDate = LocalDate.now();
					System.out.println(currentDate);
					if (currentDate.isEqual(transactionDate)) {
						System.out.println("V ifa sum");
						/// redirect kym stranica da te pita dali iskash da plati smetkata
						plannedTransactionDAO.payPlannedTransaction(transaction);
						System.out.println("balance" + transaction.getAccount().getBalance());
					}
				}
			} catch (PlannedTransactionException e) {
				e.printStackTrace();
			}
		}

	}
}
