package com.financetracker.model.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.UserException;

public class UserDAO implements IUserDAO {
	private static final String LOGIN_USER_SQL = "SELECT * FROM users WHERE email=? and password=sha1(?)";
	private static final String ADD_USER_SQL = "INSERT INTO users(email, password) VALUES (?, sha1(?))";
	private static final String CHECK_USER_IF_EXISTS = "SELECT * FROM users where email =?";
	private static UserDAO userDAO = null;

	private UserDAO() {

	}

	public static UserDAO getInstance() {
		if (userDAO == null) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}

	@Override
	public boolean login(String email, String password) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getInstance().getConnection().prepareStatement(LOGIN_USER_SQL);
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet resultSet = pstmt.executeQuery();

			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new UserException("Can't connect to Database !", e);
		}
	}

//	@Override
//	public boolean login(String email, String password) throws ClassNotFoundException, SQLException {
//		PreparedStatement pstmt = DBConnection.getInstance().getConnection().prepareStatement(LOGIN_USER_SQL);
//		pstmt.setString(1, email);
//		pstmt.setString(2, password);
//		ResultSet rs = pstmt.executeQuery();
//		if (rs.next()) {
//			pstmt.close();
//			return true;
//		}
//		pstmt.close();
//		return false;
//	}

	@Override
	public int register(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (checkIfUserExists(user)) {
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(ADD_USER_SQL,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, user.getEmail());
				pstmt.setString(2, user.getPassword());

				pstmt.executeUpdate();

				ResultSet resultSet = pstmt.getGeneratedKeys();
				resultSet.next();

				return resultSet.getInt(1);
			} else {
				throw new UserException("User already exist");
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new UserException("User with that email already exists!", e);
		}
	}

	@Override
	public boolean checkIfUserExists(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getInstance().getConnection().prepareStatement(CHECK_USER_IF_EXISTS);
			pstmt.setString(1, user.getEmail());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				// pstmt.close();
				return false;
			} else {
				// pstmt.close();
				return true;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new UserException("User with that email already exists!", e);
		}
	}

}
