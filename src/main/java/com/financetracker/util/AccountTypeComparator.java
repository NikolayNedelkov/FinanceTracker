package com.financetracker.util;

import java.util.Comparator;

import com.financetracker.model.accounts.Account;

public class AccountTypeComparator implements Comparator<Account> {
	@Override
	public int compare(Account a1, Account a2) {
		if (a1.getType().equals(a2.getType())) {
			return a1.getAccountName().compareTo(a2.getAccountName());
		}
		return a1.getType().compareToIgnoreCase(a2.getType());
	}
}
