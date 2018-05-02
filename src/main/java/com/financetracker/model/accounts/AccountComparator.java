package com.financetracker.model.accounts;

import java.util.Comparator;

public class AccountComparator implements Comparator<Account> {

	@Override
	public int compare(Account a1, Account a2) {
		if ((a1.getAccountName().compareTo(a2.getAccountName()) == 0)
				&& (a1.getCurrency().compareTo(a2.getCurrency()) == 0) && (a1.getType().compareTo(a2.getType()) == 0)
				&& (a1.getBalance() == a2.getBalance())) {
			return 0;
		}
		return -1;
	}

}
