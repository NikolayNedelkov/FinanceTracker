package com.financetracker.model.transactions;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.financetracker.exceptions.TransactionException;
import com.financetracker.model.accounts.Account;

public class Transaction {
	private int id;
	private String payee;
	private double amount;
	private LocalDate date;
	private Account account;
	private boolean isIncome;
	private String category;
	private int plannedTransactionId;

	public Transaction(String payee, double amount, LocalDate date, Account account, String category, boolean isIncome)
			throws TransactionException {
		setPayee(payee);
		setAmount(amount);
		setDate(date);
		setAccount(account);
		this.isIncome = isIncome;
		setCategory(category);
	}

	public Transaction(int id, String payee, double amount, LocalDate date, Account account, String category,
			boolean isIncome) throws TransactionException {
		setId(id);
		setPayee(payee);
		setAmount(amount);
		setDate(date);
		setAccount(account);
		this.isIncome = isIncome;
		setCategory(category);
	}

	public Transaction(String payee, double amount, LocalDate date, boolean isIncome) throws TransactionException {
		setId(id);
		setPayee(payee);
		setAmount(amount);
		setDate(date);
		setAccount(account);
		this.isIncome = isIncome;
		setCategory(category);
	}

	public Transaction(String payee, double amount, Account account, String category, boolean isIncome)
			throws TransactionException {
		setPayee(payee);
		setAmount(amount);
		setAccount(account);
		this.isIncome = isIncome;
		setCategory(category);
	}

	public Transaction(String payee, double amount, Account account, String category, boolean isIncome,
			int plannedTransactionId) throws TransactionException {
		setPayee(payee);
		setAmount(amount);
		setAccount(account);
		this.isIncome = isIncome;
		setCategory(category);
		setPlannedTransactionId(plannedTransactionId);
	}

	public boolean isValidString(String string) {
		if (string != null && string.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws TransactionException {
		if (id > 0) {
			this.id = id;
		} else {
			throw new TransactionException("Invalid id!");
		}
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) throws TransactionException {
		if (isValidString(payee)) {
			this.payee = payee;
		} else {
			throw new TransactionException("Invalid payee or payer!");
		}
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) throws TransactionException {
		if (amount > 0) {
			this.amount = amount;
		} else {
			throw new TransactionException("Invalid amount for transaction!");
		}

	}

	public LocalDate getDate() {
		return date;
	}

	private void setDate(LocalDate date) throws TransactionException {
		if (date != null) {
			this.date = date;
		} else {
			throw new TransactionException("Invalid date!");
		}
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) throws TransactionException {
		if (account != null) {
			this.account = account;
		} else {
			throw new TransactionException("Invalid account!");
		}

	}

	public void setIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}

	public boolean getIsIncome() {
		return isIncome;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) throws TransactionException {
		if (category != null) {
			this.category = category;
		} else {
			throw new TransactionException("Invalid category!");
		}

	}

	public int getPlannedTransactionId() {
		return plannedTransactionId;
	}

	public void setPlannedTransactionId(int plannedTransactionId) {
		if (plannedTransactionId >= 0) {
			this.plannedTransactionId = plannedTransactionId;
		}
	}
}
