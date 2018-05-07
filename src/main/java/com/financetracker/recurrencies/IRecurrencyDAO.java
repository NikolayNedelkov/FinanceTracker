package com.financetracker.recurrencies;


import java.util.SortedSet;

import com.financetracker.exceptions.RecurrencyException;

public interface IRecurrencyDAO {
	
	int getRecurrencyIdByName(String recurrency) throws RecurrencyException;
	
	String getRecurrencyNameById(int id) throws RecurrencyException;
	
	SortedSet<String> getAllRecurrencies() throws RecurrencyException;
}
