package com.financetracker.model.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.UserException;

public class UserDAO implements IUserDAO {
	private static final String GET_USER_BY_EMAIL = "SELECT id, password, last_loged_in FROM users where email like ?;";
	private static final String LOGIN_USER_SQL = "SELECT * FROM users WHERE email=? and password=sha1(?)";
	private static final String ADD_USER_SQL = "INSERT INTO users(first_name, last_name, email, password) VALUES (?, ?, ?, sha1(?));";
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

	@Override
	public int register(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (checkIfUserExists(user)) {
				pstmt = DBConnection.getInstance().getConnection().prepareStatement(ADD_USER_SQL,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, user.getFirstName());
				pstmt.setString(2, user.getLastName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getPassword());

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

	public User getUserByEmail(String email) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getInstance().getConnection().prepareStatement(GET_USER_BY_EMAIL);
			pstmt.setString(1, email);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String password = rs.getString("password");
				LocalDateTime lastLoggedIn = rs.getTimestamp("last_loged_in").toLocalDateTime();
				User user = new User(id, email, password, lastLoggedIn);
				pstmt.close();
				return user;
			} else {
				throw new UserException("User with email:" + email + " doesn't exist!");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new UserException("User with that email already exists!", e);
		}
	}
}
