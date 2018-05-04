package com.financetracker.model.categories;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import com.financetracker.exceptions.TransactionException;

public interface ICategoryDAO {

	int getCategoryID(String category) throws TransactionException;

	SortedSet<String> getCategoriesByType(String isIncome) throws SQLException, TransactionException;
	
	SortedSet<String> getCategoriesByPrefix(String prefix) throws TransactionException;

	SortedSet<String> getAllCategories() throws ClassNotFoundException, SQLException;
}