package com.financetracker.util;

import java.util.Comparator;

import com.financetracker.model.accounts.Account;

public class AccountNameComparator implements Comparator<Account>{
	@Override
	public int compare(Account a1, Account a2) {
		if ((a1.getAccountName().compareTo(a2.getAccountName()) == 0)) {
			return a1.getCurrency().compareTo(a2.getCurrency());
		}
		return a1.getAccountName().compareTo(a2.getAccountName());
	}
}
