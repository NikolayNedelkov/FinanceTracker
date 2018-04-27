package com.financetracker.model.currencies;

import java.sql.SQLException;

public class CurrencyDAO {
	private static CurrencyDAO instance = null;

	private CurrencyDAO() {
	}

	public static CurrencyDAO getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new CurrencyDAO();
		}
		return instance;
	}
	
}
