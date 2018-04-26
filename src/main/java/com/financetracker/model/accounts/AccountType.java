package com.financetracker.model.accounts;

public class AccountType {
	private int accountTypeId;
	private String type;
	private AccountType_Sort sort;

	public AccountType(String type, AccountType_Sort sort) {
		this.type = type;
		// this.sort = sort;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AccountType_Sort getSort() {
		return sort;
	}

	public void setSort(AccountType_Sort sort) {
		this.sort = sort;
	}

}
