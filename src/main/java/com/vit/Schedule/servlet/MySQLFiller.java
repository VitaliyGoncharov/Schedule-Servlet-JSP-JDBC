package com.vit.Schedule.servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.FileUtils;
import com.vit.Schedule.util.JdbcConnection;
import com.vit.Schedule.util.Settings;

public class MySQLFiller extends HttpServlet {
	private static final long serialVersionUID = -3290792043438858315L;
	
	private static Logger log = Logger.getLogger(MySQLFiller.class.getName());
	private static final String SCHEMA_FILE = "schema.sql";
	private static final String DATA_FILE = "data.sql";
	
	/**
	 * For creating database and persisting data without launching Web App
	 * 
	 * @param args
	 * @throws ServletException
	 */
	public static void main(String... args) throws ServletException {
		MySQLFiller filler = new MySQLFiller();
		filler.init();
	}

	public void init() throws ServletException {
		Settings settings = Settings.getInstance();
		String mode = settings.value("jdbc.mode");
		
		switch (mode.toLowerCase()) {
			case "update":
				dropTables();
				createSchema();
				populateWithData();
				break;
			case "create":
				dropDatabase();
				createDatabase();
				createSchema();
				populateWithData();
				break;
			case "create-drop":
				createDatabase();
				createSchema();
				dropDatabase();
				break;
		}
		log.info("Executed database operations using \"" + mode + "\" mode");
	}
	
	public void createSchema() {
		List<String> tablesSchemas = getTableSchemas();
		
		Connection connection = JdbcConnection.getConnection();
		Statement statement = null;
		
		try {
			connection.setCatalog("schedule");
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			
			for (String tableSchema : tablesSchemas) {
				statement.executeUpdate(tableSchema);
			}
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try { connection.rollback(); } catch (SQLException ex) { }
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	private void createDatabase() {
		Connection connection = JdbcConnection.getConnection("");
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS schedule");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	private void dropDatabase() {
		Connection connection = JdbcConnection.getConnection("");
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate("DROP DATABASE IF EXISTS schedule");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	private List<String> getTableSchemas() {
		String fileStr = FileUtils.getString(getClass().getClassLoader().getResource(SCHEMA_FILE).getFile());
		return Arrays.asList(fileStr.split("\r\n\r\n"));
	}
	
	private List<String> getTablesData() {
		String fileStr = FileUtils.getString(getClass().getClassLoader().getResource(DATA_FILE).getFile());
		List<String> fileLines = Arrays.asList(fileStr.split("\r\n"));
		return fileLines.stream()
			.filter(x -> x != null && !x.equals("") && !x.startsWith("#"))
			.collect(Collectors.toList());
	}
	
	private void populateWithData() {
		Connection connection = JdbcConnection.getConnection();
		List<String> tablesData = getTablesData();
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);
			for (String tableData : tablesData) {
				statement.executeUpdate(tableData);
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try { connection.rollback(); } catch (SQLException ex) {}
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	private void dropTables() {
		Connection connection = JdbcConnection.getConnection();
		Statement statement = null;
		
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS schedule");
			statement.executeUpdate("DROP TABLE IF EXISTS day");
			statement.executeUpdate("DROP TABLE IF EXISTS subject");
			statement.executeUpdate("DROP TABLE IF EXISTS groups");
			statement.executeUpdate("DROP TABLE IF EXISTS major");
			statement.executeUpdate("DROP TABLE IF EXISTS school");
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try { connection.rollback(); } catch (SQLException ex) {}
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	@SuppressWarnings("unused")
	private static Timestamp getCurrentTimeStamp() {
		Date today = new Date();
		return new Timestamp(today.getTime());
	}
}
