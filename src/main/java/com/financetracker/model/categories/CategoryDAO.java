package com.financetracker.model.categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.stereotype.Component;
import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.CategoryException;
import com.financetracker.model.users.User;

@Component
public class CategoryDAO implements ICategoryDAO {

	private static final String GET_CATEGORIES_BY_TYPE_SQL = "SELECT categories.name FROM categories WHERE categories.is_income = ?";
	private static final String GET_ALL_CATEGORIES_SQL = "SELECT categories.name FROM categories";
	private static final String GET_CATEGORY_ID_SQL = "SELECT categories.id FROM categories WHERE categories.name = ?";
	private static final String GET_CATEGORY_NAME_SQL = "SELECT categories.name FROM categories WHERE categories.id = ?";
	private static final String ADD_NEW_CATEGORY_SQL = "INSERT INTO categories VALUES (null,?,?)";
	private static final String CHECK_IF_CATEGORY_EXISTS = "SELECT categories.name FROM categories WHERE categories.name = ? AND categories.is_income =?";


	@Override
	public int getCategoryIdByName(String category) throws CategoryException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_CATEGORY_ID_SQL);
			statement.setString(1,category);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new CategoryException("Cannot find category name!", e);
		}
	}
	
	
	@Override
	public SortedSet<String> getAllCategories() throws CategoryException{
		Connection connection;
		try {
			connection = DBConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(GET_ALL_CATEGORIES_SQL);
			TreeSet<String> allCategories = new TreeSet<>();
			while (resultSet.next()) {
				allCategories.add((String) resultSet.getString("name"));
			}
			return allCategories;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new CategoryException("Cannot get categories!", e);
		}
	}
	
	@Override
	public SortedSet<String> getCategoriesByType(String isIncome) throws CategoryException{
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_CATEGORIES_BY_TYPE_SQL);
			
			if(isIncome.equals("true")) {
				statement.setBoolean(1, true);
			}else {
				statement.setBoolean(1, false);
			}
			
			ResultSet resultSet = statement.executeQuery();
			TreeSet<String> categories = new TreeSet<>();
			while (resultSet.next()) {
				categories.add((String) resultSet.getString("name"));
			}
			return categories;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new CategoryException("Cannot get category type!", e);
		}
	}


	@Override
	public String getCategoryNameById(int id) throws CategoryException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_CATEGORY_NAME_SQL);
			statement.setInt(1,id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getString(1);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new CategoryException("Cannot find category id!", e);
		}
	}


	@Override
	public int addNewCategory(String categoryName, Boolean type) throws CategoryException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(CHECK_IF_CATEGORY_EXISTS);
			statement.setString(1, categoryName);
			statement.setBoolean(2, type);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				throw new CategoryException("Category already exists!");
			}else {
				PreparedStatement newStatement = connection.prepareStatement(ADD_NEW_CATEGORY_SQL,Statement.RETURN_GENERATED_KEYS);
				newStatement.setString(1, categoryName);
				newStatement.setBoolean(2, type);
				newStatement.executeUpdate();
				ResultSet resultSet = newStatement.getGeneratedKeys();
				resultSet.next();
				return resultSet.getInt(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new CategoryException("Cannot add category!", e);
		}
		
		
	}
}
