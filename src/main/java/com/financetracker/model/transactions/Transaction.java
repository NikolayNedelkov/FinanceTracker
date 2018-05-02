package com.financetracker.model.transactions;

import java.time.LocalDate;

import com.financetracker.exceptions.TransactionException;

public class Transaction {
	private int id;
	private String payee;
	private double amount;
	private LocalDate date;
	private int accountID;
	private boolean isIncome;
	private int category;
	
	public Transaction() {
		
	}

	public Transaction(String payee, double amount,LocalDate date, int account, int category,boolean isIncome)
			throws TransactionException {
		setPayee(payee);
		setAmount(amount);
		setDate(date);
		setAccount(account);
		this.isIncome = isIncome;
		setCategory(category);
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
		}else {
			throw new TransactionException("Invalid amount for transaction!");
		}
		
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	private void setDate(LocalDate date) throws TransactionException {
		if(date != null) {
			this.date = date;
		} else {
			throw new TransactionException("Invalid date!");
		}
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccount(int accountID) throws TransactionException {
		if (accountID > 0) {
			this.accountID = accountID;
		}else {
			throw new TransactionException("Invalid account!");
		}

	}

	public boolean getIsIncome() {
		return isIncome;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) throws TransactionException {
		if (category > 0) {
			this.category = category;
		}else {
			throw new TransactionException("Invalid category!");
		}
	
	}

	
	

}
