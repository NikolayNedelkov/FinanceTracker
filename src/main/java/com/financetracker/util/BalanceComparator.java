package com.financetracker.util;

import static org.mockito.Matchers.intThat;

import java.util.Comparator;

import com.financetracker.model.accounts.Account;

public class BalanceComparator implements Comparator<Account> {
	@Override
	public int compare(Account a1, Account a2) {
		if (a1.getBalance() == a2.getBalance()) {
			return 0;
		}

		return ((a1.getBalance() - a2.getBalance())>0)?1:-1;
	}
}
