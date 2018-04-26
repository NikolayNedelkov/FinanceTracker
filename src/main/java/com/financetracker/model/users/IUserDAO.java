package com.financetracker.model.users;

import java.sql.SQLException;

import com.financetracker.exceptions.UserException;

public interface IUserDAO {
	

	int register(User user) throws UserException;

	boolean checkIfUserExists(User user) throws UserException;

	boolean login(String email, String password) throws UserException, ClassNotFoundException, SQLException;
}
