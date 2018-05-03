package com.financetracker.model.users;

import java.sql.SQLException;

import com.financetracker.exceptions.UserException;

public interface IUserDAO {

	boolean login(String email, String password) throws UserException, ClassNotFoundException, SQLException;

	int register(User user) throws UserException;

	boolean checkIfUserExists(User user) throws UserException;

	User getUserByEmail(String email) throws UserException;

	void changePassword(int id, String password, String newPassword, String newPassword2) throws UserException;

}