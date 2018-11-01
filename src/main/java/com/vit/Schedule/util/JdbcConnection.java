package com.vit.Schedule.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
	
	private final Settings settings;
	private Connection connection;
	
	private JdbcConnection() {
		this(null);
	}
	
	private JdbcConnection(String database) {
		settings = Settings.getInstance();
		String db = settings.value("jdbc.database");
		String url = settings.value("jdbc.url");
		String user = settings.value("jdbc.username");
		String password = settings.value("jdbc.password");
		
		if (database == null) {
			url = url + db;
		}
		
		if (database != null || database != null && database.equals("")) {
			url = url + database;
		}
		
		try {
			Class.forName(settings.value("jdbc.driver_class"));
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return new JdbcConnection().connection;
	}
	
	public static Connection getConnection(String database) {
		return new JdbcConnection(database).connection;
	}
}
