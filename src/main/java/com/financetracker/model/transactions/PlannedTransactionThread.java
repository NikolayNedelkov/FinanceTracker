package com.financetracker.model.transactions;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.exceptions.PlannedTransactionException;

@Component
public class PlannedTransactionThread extends Thread {

	@Autowired
	private PlannedTransactionDAO plannedTransactionDAO;

	public PlannedTransactionThread() {
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
				for (PlannedTransaction transaction : allTransactions) {
					LocalDate currentDate = LocalDate.now();
					LocalDate plannedDate = transaction.getPlannedDate();

					if(currentDate.equals(plannedDate)) {
						plannedTransactionDAO.payPlannedTransaction(transaction);
						String recurrency = transaction.getRecurrency();
												
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
						plannedTransactionDAO.updatePlannedTransactionDate(transaction, plannedDate);
					}
				}
			} catch (PlannedTransactionException e) {
				e.printStackTrace();
			}
		}

	}
}
