package com.financetracker.model.accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.security.auth.login.AccountException;

import com.financetracker.model.transactions.Transaction;
import com.financetracker.model.users.User;

public class Account {
	private int account_id;
	private User user;
	private String accountName;
	private double balance;
	// private Currency currency;
	private int currency;
	private AccountType type;
	private List<Transaction> transactions;

	public Account() {
		this.transactions = new ArrayList<Transaction>();
	}

	public Account(User user, String name, double balance, AccountType type) throws AccountException {
		setAccountName(name);
		setType(type);
		this.user = user;
		this.balance = balance;
		this.transactions = new ArrayList<Transaction>();
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

	public int getCurrency() {
		return currency;
	}

	// public void setCurrency(Currency currency) throws AccountException {
	// if (currency != null)
	// this.currency = currency;
	// else
	// throw new AccountException();
	// }

	public void setCurrency(int currency) throws AccountException {
		if (currency > 0)
			this.currency = currency;
		else
			throw new AccountException();
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) throws AccountException {
		if (type != null) {
			this.type = type;
		} else
			throw new AccountException("Not a valid accounttype");
	}

	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(this.transactions);
	}

}
