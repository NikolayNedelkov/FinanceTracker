package com.financetracker.model.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.financetracker.exceptions.AccountException;
import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.users.User;

public class Account{
	private int account_id;
	private User user;
	private String accountName;
	private double balance;
	// private Currency currency;
	private String lastFourDigits;
	private float percentage;
	private int paymentDueDay;
	private String currency;
	private String type;
	// private List<Transaction> transactions;

	// public Account() {
	// // this.transactions = new ArrayList<Transaction>();
	// }

	// public Account(User user, String name, double balance, String currency,
	// String type) throws AccountException {
	// setAccountName(name);
	// setType(type);
	// setUser(user);
	// this.balance = balance;
	// setCurrency(currency);
	// // this.transactions = new ArrayList<Transaction>();
	// }
	//
	// public Account(int id, User user, String name, double balance, String
	// currency, String type)
	// throws AccountException {
	// this.account_id = id;
	// setAccountName(name);
	// setType(type);
	// setUser(user);
	// this.balance = balance;
	// setCurrency(currency);
	// // this.transactions = new ArrayList<Transaction>();
	// }

	public Account() {
	}

	public Account(int account_id, User user, String accountName, double balance, String lastFourDigits,
			float percentage, int paymentDueDay, String currency, String type) throws AccountException {
		this.account_id = account_id;
		this.user = user;
		setAccountName(accountName);
		setType(type);
		setUser(user);
		this.balance = balance;
		setCurrency(currency);
		this.lastFourDigits = lastFourDigits;
		this.percentage = percentage;
		this.paymentDueDay = paymentDueDay;
	}

	public Account(User user, String accountName, double balance, String lastFourDigits, float percentage,
			int paymentDueDay, String currency, String type) throws AccountException {
		setAccountName(accountName);
		setType(type);
		setUser(user);
		this.balance = balance;
		setCurrency(currency);
		this.lastFourDigits = lastFourDigits;
		this.percentage = percentage;
		this.paymentDueDay = paymentDueDay;
	}

	public Account(int account_id, String accountName, double balance, String lastFourDigits, float percentage,
			int paymentDueDay, String currency, String type) throws AccountException {
		this.account_id = account_id;
		setAccountName(accountName);
		setType(type);
		this.balance = balance;
		setCurrency(currency);
		this.lastFourDigits = lastFourDigits;
		this.percentage = percentage;
		this.paymentDueDay = paymentDueDay;
	}

	public String getLastFourDigits() {
		return lastFourDigits;
	}

	public void setLastFourDigits(String lastFourDigits) {
		this.lastFourDigits = lastFourDigits;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public int getPaymentDueDay() {
		return paymentDueDay;
	}

	public void setPaymentDueDay(int paymentDueDay) {
		this.paymentDueDay = paymentDueDay;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) throws AccountException {
		if (user != null)
			this.user = user;
		else
			throw new AccountException();
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) throws AccountException {
		if ((accountName != null) && (accountName.trim().length() > 0))
			this.accountName = accountName;
		else
			throw new AccountException("Invalid account name!");
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	// public Currency getCurrency() {
	// return currency;
	// }

	public String getCurrency() {
		return currency;
	}
	
	
	
	// public void setCurrency(Currency currency) throws AccountException {
	// if (currency != null)
	// this.currency = currency;
	// else
	// throw new AccountException();
	// }

	public void setCurrency(String currency) throws AccountException {
		if ((currency != null) && (currency.trim().length() > 0))
			this.currency = currency;
		else
			throw new AccountException();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) throws AccountException {
		if ((type != null) && (type.trim().length() > 0)) {
			this.type = type;
		} else
			throw new AccountException("Not a valid accounttype");
	}

	// public List<Transaction> getTransactions() {
	// return Collections.unmodifiableList(this.transactions);
	// }

}
