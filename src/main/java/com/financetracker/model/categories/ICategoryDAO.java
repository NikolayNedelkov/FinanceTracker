package com.financetracker.model.categories;

import java.sql.SQLException;
import java.util.TreeSet;

import com.financetracker.exceptions.TransactionException;

public interface ICategoryDAO {

	int getCategoryID(String category) throws TransactionException;

	TreeSet<String> getCategories(String isIncome) throws SQLException, TransactionException;

}