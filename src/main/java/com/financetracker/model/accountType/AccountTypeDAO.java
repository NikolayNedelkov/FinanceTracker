package com.financetracker.model.accountType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CurrencyException;
import com.financetracker.model.currencies.CurrencyDAO;

@Component
public class AccountTypeDAO {
	private static final String GET_ACC_TYPE_ID = "SELECT id from account_types where type like ?;";
	private static final String GET_ALL_ACCOUNTTYPES = "SELECT type FROM account_types;";

	public int getAccountTypeId(String typeName) throws SQLException, ClassNotFoundException, AccountException {
		PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(GET_ACC_TYPE_ID,
				Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, typeName);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		} else {
			throw new AccountException("Not such accounttype in DB!");
		}
	}

	public List<String> getAccountTypesFromDB() throws ClassNotFoundException, SQLException, AccountException {
		List<String> allAccountTypes = new ArrayList<String>();
		Statement st = DBConnection.getInstance().getConnection().createStatement();
		ResultSet rs = st.executeQuery(GET_ALL_ACCOUNTTYPES);
		while (rs.next()) {
			allAccountTypes.add((String) rs.getString("type"));
		}
		return allAccountTypes;
	}
}
