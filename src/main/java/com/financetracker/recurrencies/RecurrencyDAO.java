package com.financetracker.recurrencies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.financetracker.database.DBConnection;
import com.financetracker.exceptions.CategoryException;
import com.financetracker.exceptions.RecurrencyException;

@Component
public class RecurrencyDAO implements IRecurrencyDAO{

	private static final String GET_RECURRENCY_ID_SQL = "SELECT recurencies.id FROM recurencies WHERE recurencies.recurency_name = ?";
	private static final String GET_RECURRENCY_NAME_SQL = "SELECT recurencies.recurency_name FROM recurencies WHERE recurencies.id = ?";
	private static final String GET_ALL_RECURRENCIES_SQL = "SELECT recurencies.recurency_name FROM recurencies;";

	
	@Override
	public int getRecurrencyIdByName(String recurrencyName) throws RecurrencyException {
		
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_RECURRENCY_ID_SQL);
			statement.setString(1,recurrencyName);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RecurrencyException("Cannot find reccurency name!", e);
		}
	}

	@Override
	public String getRecurrencyNameById(int id) throws RecurrencyException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(GET_RECURRENCY_NAME_SQL);
			statement.setInt(1,id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getString(1);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new RecurrencyException("Cannot find reccurency id!", e);
		}
	}

	@Override
	public SortedSet<String> getAllRecurrencies() throws RecurrencyException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(GET_ALL_RECURRENCIES_SQL);
			TreeSet<String> allRecurrencies = new TreeSet<>();
			while (resultSet.next()) {
				allRecurrencies.add(resultSet.getString("recurency_name"));
			}
			return allRecurrencies;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new RecurrencyException("Cannot get recurrencies!", e);
		}
	}
	
}
