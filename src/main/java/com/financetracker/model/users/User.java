package com.financetracker.model.users;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.budget.Budget;


@Scope("session")
public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private LocalDateTime lastLoggedIn;
	private Budget budget;
	private Set<Account> accounts;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.accounts = new HashSet<Account>();
	}
	
	public User(String email, String password, String firstName, String lastName) {
		this(email, password);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User(int id, String email, String password, LocalDateTime lastLoggedIn) {
		this(email, password);
		this.id = id;
		this.lastLoggedIn = lastLoggedIn;
	}
	
	

	public User(int id, String firstName, String lastName, String email, String password, LocalDateTime lastLoggedIn) {
		this(id, email, password, lastLoggedIn);
		this.firstName = firstName;
		this.lastName = lastName;
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

	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
}
