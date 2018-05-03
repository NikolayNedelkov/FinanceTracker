package com.financetracker.model.accountType;

import java.sql.SQLException;
import java.util.List;

import com.financetracker.exceptions.AccountException;

public interface IAccountTypeDAO {

	int getAccountTypeId(String typeName) throws SQLException, ClassNotFoundException, AccountException;

	List<String> getAccountTypesFromDB() throws ClassNotFoundException, SQLException, AccountException;

}