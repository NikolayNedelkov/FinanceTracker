package com.financetracker.model.users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.budget.Budget;

public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private LocalDateTime lastLoggedIn;
	private Budget budget;
	private List<Account> accounts;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.accounts = new ArrayList<Account>();
	}

	public User(int id, String email, String password, LocalDateTime lastLoggedIn) {
		this(email, password);
		this.id = id;
		this.lastLoggedIn = lastLoggedIn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws UserException {
		if (password != null && password.trim().length() > 0) {
			this.password = password;
		} else
			throw new UserException("invalid password");

	}

	public LocalDateTime getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(LocalDateTime lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

}
