package com.financetracker.model.currencies;

import java.sql.SQLException;
import java.util.List;

import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CurrencyException;

public interface ICurrencyDAO {

	int getCurrencyId(String currencyName) throws SQLException, ClassNotFoundException, AccountException;

	List<String> getCurrenciesFromDB() throws ClassNotFoundException, SQLException, CurrencyException;

}