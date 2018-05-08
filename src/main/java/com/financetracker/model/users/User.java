package com.financetracker.model.users;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.accounts.Account;
import com.financetracker.model.budget.Budget;
import com.financetracker.util.EmailChecker;
import com.financetracker.util.SecureTokenGenerator;

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
	private String passwordToken;

	public User() {

	}

	public User(String email, String password) throws UserException {
		setEmail(email);
		setPassword(password);
		this.accounts = new HashSet<Account>();
	}

	public User(String email, String password, String firstName, String lastName) throws UserException {
		this(email, password);
		setFirstName(firstName);
		setLastName(lastName);
		this.passwordToken = SecureTokenGenerator.nextToken();
	}

	public User(int id, String email, String password, LocalDateTime lastLoggedIn) throws UserException {
		this(email, password);
		setId(id);
		setLastLoggedIn(lastLoggedIn);
	}

	public User(int id, String firstName, String lastName, String email, String password, LocalDateTime lastLoggedIn) throws UserException {
		this(id, email, password, lastLoggedIn);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public User(int id, String firstName, String lastName, String email, String password, LocalDateTime lastLoggedIn,
			String passwordToken) throws UserException {
		this(email, password);
		setId(id);
		setLastLoggedIn(lastLoggedIn);
		setFirstName(firstName);
		setLastName(lastName);
		setPasswordToken(passwordToken);
	}

	public String getPasswordToken() {
		return passwordToken;
	}

	public void setPasswordToken(String passwordToken) throws UserException {
		if (passwordToken != null && passwordToken.trim().length() > 2) {
			this.passwordToken = passwordToken;
		} else
			throw new UserException("Invalid Token");
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

	public void setFirstName(String firstName) throws UserException {
		if (firstName != null && firstName.trim().length() > 2) {
			this.firstName = firstName;
		} else
			throw new UserException("Invalid Username");
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws UserException {
		if (lastName != null && lastName.trim().length() > 2) {
			this.lastName = lastName;
		} else
			throw new UserException("Invalid Username");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws UserException {
		if (EmailChecker.validate(email)) {
			this.email = email;
		} else
			throw new UserException("Invalid Email");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws UserException {
		if (password != null && password.trim().length() > 0) {
			this.password = password;
		} else
			throw new UserException("Invalid password");

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

	public void setBudget(Budget budget) throws UserException {
		if (budget != null) {
			this.budget = budget;
		} else {
			throw new UserException("Invalid budget");
		}
	}

	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
}
