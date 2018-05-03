package com.financetracker.model.budget;

import com.financetracker.exceptions.UserException;
import com.financetracker.model.users.User;

public interface IBudgetDAO {

	double calculateIncome(User user) throws UserException;

	double calculateExpense(User user) throws UserException;

	Budget getBudget(User u) throws UserException;

}