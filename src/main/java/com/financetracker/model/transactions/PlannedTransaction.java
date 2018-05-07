package com.financetracker.model.transactions;

import java.time.LocalDate;

import com.financetracker.exceptions.PlannedTransactionException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;

public class PlannedTransaction extends Transaction{
	private LocalDate plannedDate;
	private String recurrency;

	public PlannedTransaction(String payee, double amount, LocalDate plannedDate, Account account, String category,
			boolean isIncome, String recurrency) throws TransactionException, PlannedTransactionException {
		super(payee, amount, account, category, isIncome);
		setPlannedDate(plannedDate);
		setRecurrency(recurrency);
	}
	
	public PlannedTransaction(int id,String payee, double amount, LocalDate plannedDate, Account account, String category,
			boolean isIncome, String recurrency) throws TransactionException, PlannedTransactionException {
		super(payee, amount, account, category, isIncome);
		setId(id);
		setPlannedDate(plannedDate);
		setRecurrency(recurrency);
	}

	public LocalDate getPlannedDate() {
		return plannedDate;
	}

	public void setPlannedDate(LocalDate plannedDate) throws PlannedTransactionException {
		if (plannedDate != null) {
			this.plannedDate = plannedDate;
		} else {
			throw new PlannedTransactionException("Invalid planned date!");
		}
	}

	public String getRecurrency() {
		return recurrency;
	}

	public void setRecurrency(String recurrency) throws PlannedTransactionException {
		if (recurrency != null && recurrency.trim().length() > 0) {
			this.recurrency = recurrency;
		}else {
			throw new PlannedTransactionException("Invalid recurrency!");
		}
	}

}
