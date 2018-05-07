package com.financetracker.model.categories;

import java.util.SortedSet;
import com.financetracker.exceptions.CategoryException;

public interface ICategoryDAO {

	int getCategoryIdByName(String category) throws CategoryException;
	
	String getCategoryNameById(int id) throws CategoryException;

	SortedSet<String> getCategoriesByType(String isIncome) throws CategoryException;
	
	SortedSet<String> getAllCategories() throws CategoryException;
	
}