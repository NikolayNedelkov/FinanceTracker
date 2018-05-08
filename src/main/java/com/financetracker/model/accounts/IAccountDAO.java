package com.financetracker.model.accounts;

import java.util.Collection;
import java.util.HashSet;

import javax.servlet.http.HttpSession;

import com.financetracker.exceptions.AccountException;
import com.financetracker.model.users.User;

public interface IAccountDAO {

	int addNewAccount(Account a, HttpSession session) throws AccountException;

	Collection<Account> getAllAccountsForUser(User user) throws AccountException;

	// Validaciq posle
	Account getAccountById(int id) throws AccountException;

	Account getAccountByName(String name) throws AccountException;

	void updateAccount(Account updated) throws AccountException;

}