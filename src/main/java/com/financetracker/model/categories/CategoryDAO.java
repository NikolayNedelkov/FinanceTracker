package com.financetracker.model.categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.TransactionException;

@Component
public class CategoryDAO {

	
	private static final String GET_ALL_CATEGORIES_SQL = "SELECT categories.name FROM categories WHERE categories.is_income = ?";
	private static final String GET_CATEGORY_ID_SQL = "SELECT categories.id FROM categories WHERE categories.name = ?";

	public int getCategoryID(String category) throws TransactionException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_CATEGORY_ID_SQL);
			statement.setString(1,category);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot find category name!", e);
		}
	}
	
	public TreeSet<String> getCategories(String isIncome) throws SQLException, TransactionException{
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_ALL_CATEGORIES_SQL);
			
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new TransactionException("Cannot get categories!", e);
		}
	}
}
