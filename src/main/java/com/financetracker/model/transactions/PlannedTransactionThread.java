package com.financetracker.model.transactions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.stereotype.Component;

import com.financetracker.exceptions.PlannedTransactionException;

@Component
public class PlannedTransactionThread extends Thread {

	@Autowired
	private PlannedTransactionDAO plannedTransactionDAO;

	public PlannedTransactionThread() {
		System.out.println("Thread started...");
		 this.setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				return;
			}
			try {
				List<PlannedTransaction>allTransactions = plannedTransactionDAO.getAllUsersPlannedTransactions();
				System.out.println("vsichki"+allTransactions);
				for (PlannedTransaction transaction : allTransactions) {
					System.out.println("edin"+transaction);
					
					LocalDate currentDate = LocalDate.now();
					System.out.println("currentdate"+currentDate);
					
					LocalDate plannedDate = transaction.getPlannedDate();
					System.out.println("plannedDate" + plannedDate);

					if(currentDate.equals(plannedDate)) {
						System.out.println("sled ifa");
						plannedTransactionDAO.payPlannedTransaction(transaction);
						System.out.println("sled plashtaneto");
						String recurrency = transaction.getRecurrency();
						System.out.println(recurrency);
												
						switch (recurrency.toLowerCase()) {
						case "monthly":
							plannedDate = plannedDate.plusMonths(1);
							break;
						case "weekly":
							plannedDate = plannedDate.plusWeeks(1);
							break;
						case "daily":
							plannedDate = plannedDate.plusDays(1);
							break;
						default:
							break;
						}
						System.out.println("novata data " + plannedDate);
						System.out.println("predi update");
						System.out.println("transaction " + transaction);
						plannedTransactionDAO.updatePlannedTransactionDate(transaction, plannedDate);
					}
				}
			} catch (PlannedTransactionException e) {
				e.printStackTrace();
			}
		}

	}
}
