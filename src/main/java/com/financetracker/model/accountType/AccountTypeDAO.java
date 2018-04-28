package com.financetracker.model.accountType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.financetracker.database.DBConnection;
import com.financetracker.model.currencies.CurrencyDAO;

public class AccountTypeDAO {
	private static final String GET_ACC_TYPE_ID = "SELECT id from account_types where type like ?;";
	private static AccountTypeDAO instance = null;

	private AccountTypeDAO() {
	}

	public static AccountTypeDAO getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new AccountTypeDAO();
		}
		return instance;
	}

	public int getAccountTypeId(String typeName) throws SQLException, ClassNotFoundException {
		PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(GET_ACC_TYPE_ID,
				Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, typeName);
		ResultSet rs = pst.executeQuery();
		return rs.getInt(1);
	}
}
