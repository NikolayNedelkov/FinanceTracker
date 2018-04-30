package com.financetracker.model.currencies;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;

public class CurrencyDAO {
	private static final String GET_CURR_ID = "SELECT id from currencies where currency_type like ?;";
	private static CurrencyDAO instance = null;

	private CurrencyDAO() {
	}

	public static CurrencyDAO getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new CurrencyDAO();
		}
		return instance;
	}

	public int getCurrencyId(String currencyName) throws SQLException, ClassNotFoundException, AccountException {
		PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(GET_CURR_ID,
				Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, currencyName);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			throw new AccountException("Not such currency in DB!");
		}
	}
}
