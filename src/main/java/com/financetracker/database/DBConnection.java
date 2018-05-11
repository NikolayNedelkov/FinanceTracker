package com.financetracker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;


@Component
public class DBConnection {
	
	private final Connection connection;
	
	private static final String DB_HOST = "localhost";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "s0611dd2";
	private static final String DB_PORT = "3306";
	private static final String DB_SCHEMA = "financetracker";
	private static final String DB_USE_SSL = "&useSSL=false";

	private static DBConnection instance = null;
	
	private DBConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(
				"jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_SCHEMA + "?" + DB_USE_SSL,
				DB_USER, DB_PASS);
	}
	
	public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}
