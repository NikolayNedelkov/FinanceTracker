package com.financetracker.util;

import java.util.Comparator;

import com.financetracker.model.accounts.Account;

public class CurrencyComparator implements Comparator<Account> {
	@Override
	public int compare(Account a1, Account a2) {
		if (a1.getCurrency().equals(a2.getCurrency())) {
			return a1.getAccountName().compareTo(a2.getAccountName());
		}
		return a1.getCurrency().compareToIgnoreCase(a2.getCurrency());
	}

}
