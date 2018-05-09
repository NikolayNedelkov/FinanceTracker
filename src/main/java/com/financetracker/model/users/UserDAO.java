package com.financetracker.model.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.AccountException;
import com.financetracker.exceptions.CategoryException;
import com.financetracker.exceptions.PlannedTransactionException;
import com.financetracker.exceptions.RecurrencyException;
import com.financetracker.exceptions.TransactionException;
import com.financetracker.exceptions.UserException;
import com.financetracker.model.transactions.PlannedTransaction;

@Component
public class UserDAO implements IUserDAO {
	private static final String GET_USER_BY_EMAIL = "SELECT first_name, last_name, id, password, last_loged_in, password_token FROM users where email like ?;";
	private static final String LOGIN_USER_SQL = "SELECT * FROM users WHERE email=? and password=sha1(?)";
	private static final String ADD_USER_SQL = "INSERT INTO users(first_name, last_name, email, password, password_token) VALUES (?, ?, ?, sha1(?), ?);";
	private static final String CHECK_USER_IF_EXISTS = "SELECT * FROM users where email = ?";
	private static final String UPDATE_USER_LOG_IN_TIME = "UPDATE users set last_loged_in=current_timestamp WHERE email=? and password=sha1(?);";
	private static final String FIND_USER_BY_ID_AND_PASSWORD = "SELECT password FROM users WHERE id = ? and password like sha1(?);";
	private static final String UPDATE_USER_PASSWORD = "UPDATE users set password=sha1(?) Where id=?";
	private static final String UPDATE_USER_PASSWORD_BY_EMAIL = "UPDATE users set password=sha1(?) Where email=?";
	private static final String CHECK_USER_IF_EXISTS_BY_TOKEN = "SELECT * FROM users WHERE password_token = ?;";
	private static final String GET_USER_BY_TOKEN = "SELECT first_name, last_name, id, email, password, last_loged_in FROM users where password_token like ?;";
	private static final String ALL_USERS_OLD_LOGIN_EMIALS = "SELECT email FROM users WHERE last_loged_in <= DATE_SUB(NOW(), INTERVAL 2 WEEK);";

	@Autowired
	private DBConnection DBConnection;

	@Override
	public boolean login(String email, String password) throws UserException, ClassNotFoundException, SQLException {
		PreparedStatement pstmt;
		try {
			// user check login
			DBConnection.getConnection().setAutoCommit(false);
			pstmt = DBConnection.getConnection().prepareStatement(LOGIN_USER_SQL);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet resultSet = pstmt.executeQuery();
			// update user last log in
			pstmt = DBConnection.getConnection().prepareStatement(UPDATE_USER_LOG_IN_TIME);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			DBConnection.getConnection().commit();
			int rs = pstmt.executeUpdate();

			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw new UserException("Can't connect to Database !", e);
		} finally {
			DBConnection.getConnection().setAutoCommit(true);
		}
	}

	@Override
	public int register(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			if (checkIfUserExists(user)) {
				pstmt = DBConnection.getConnection().prepareStatement(ADD_USER_SQL, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, user.getFirstName());
				pstmt.setString(2, user.getLastName());
				pstmt.setString(3, user.getEmail());
				pstmt.setString(4, user.getPassword());
				pstmt.setString(5, user.getPasswordToken());

				pstmt.executeUpdate();

				ResultSet resultSet = pstmt.getGeneratedKeys();
				resultSet.next();
				return resultSet.getInt(1);
			} else {
				throw new UserException("User already exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("User with that email already exists!", e);
		}
	}

	@Override
	public boolean checkIfUserExists(User user) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(CHECK_USER_IF_EXISTS);
			pstmt.setString(1, user.getEmail());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("User with that email already exists!", e);
		}
	}

	@Override
	public boolean checkIfUserExistsByToken(String passwordToken) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(CHECK_USER_IF_EXISTS_BY_TOKEN);
			pstmt.setString(1, passwordToken);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("User with that token don't exist!", e);
		}
	}

	@Override
	public User getUserByToken(String passwordToken) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(GET_USER_BY_TOKEN);
			pstmt.setString(1, passwordToken);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int id = rs.getInt("id");
				String password = rs.getString("password");
				String email = rs.getString("email");
				LocalDateTime lastLoggedIn = rs.getTimestamp("last_loged_in").toLocalDateTime();
				User user = new User(id, firstName, lastName, email, password, lastLoggedIn, passwordToken);
				pstmt.close();
				return user;
			} else {
				throw new UserException("User with that token doesn't exist!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("User with that token don't exist!", e);
		}
	}

	@Override
	public User getUserByEmail(String email) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(GET_USER_BY_EMAIL);
			pstmt.setString(1, email);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				int id = rs.getInt("id");
				String password = rs.getString("password");
				LocalDateTime lastLoggedIn = rs.getTimestamp("last_loged_in").toLocalDateTime();
				String passwordToken = rs.getString("password_token");

				User user = new User(id, firstName, lastName, email, password, lastLoggedIn, passwordToken);
				pstmt.close();
				return user;
			} else {
				throw new UserException("User with email:" + email + " doesn't exist!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("User with that email already exists!", e);
		}
	}

	@Override
	public void changePassword(int id, String password, String newPassword, String newPassword2) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(FIND_USER_BY_ID_AND_PASSWORD,
					Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, id);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (newPassword.equals(newPassword2)) {
					pstmt = DBConnection.getConnection().prepareStatement(UPDATE_USER_PASSWORD);
					pstmt.setString(1, newPassword);
					pstmt.setInt(2, id);

					pstmt.executeUpdate();
				} else {
					throw new UserException("New Passwords are not equal");
				}
			} else {
				throw new UserException("User dont exist");
			}
		} catch (Exception e) {
			throw new UserException("DB is not working", e);
		}
	}

	@Override
	public void changePasswordByToken(User user, String password) throws UserException {
		PreparedStatement pstmt;
		try {
			pstmt = DBConnection.getConnection().prepareStatement(GET_USER_BY_EMAIL, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getEmail());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				pstmt = DBConnection.getConnection().prepareStatement(UPDATE_USER_PASSWORD_BY_EMAIL);
				pstmt.setString(1, password);
				pstmt.setString(2, user.getEmail());

				pstmt.executeUpdate();
			} else {
				throw new UserException("User dont exist");
			}
		} catch (Exception e) {
			throw new UserException("DB is not working", e);
		}
	}

	@Override
	public User getLoggedUser(HttpSession session) {
		return (User) session.getAttribute("user");
	}

	public List<String> getAllUsersOldEmails() throws UserException {
		try {
			Connection connection = DBConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(ALL_USERS_OLD_LOGIN_EMIALS);
			List<String> allOldEmails = new LinkedList<String>();

			while (resultSet.next()) {
				allOldEmails.add(resultSet.getString("email"));
			}
			return allOldEmails;
		} catch (SQLException e) {
			throw new UserException("DB is not working well", e);
		}
	}
}
