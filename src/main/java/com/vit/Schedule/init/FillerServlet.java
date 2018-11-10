package com.vit.Schedule.init;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.FileUtils;
import com.vit.Schedule.util.JdbcConnection;
import com.vit.Schedule.util.Settings;


public class FillerServlet extends HttpServlet {
	private static final long serialVersionUID = 3008292447457493808L;
	private static FillerServlet INSTANCE = new FillerServlet();
	private static Logger log = Logger.getLogger(FillerServlet.class.getName());
	private static final String SCHEMA_FILE = "schema.sql";
	private static final String DATA_FILE = "data.sql";
	private boolean isCreateAndDrop;
	public boolean done = false;
	
	/**
	 * For creating database and persisting data without launching Web App
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String... args) throws Exception {
		FillerServlet filler = new FillerServlet();
		filler.init();
	}
	
	private FillerServlet() {
	}
	
	public static FillerServlet getInstance() {
		return INSTANCE;
	}

	public boolean isCreateAndDrop() {
		return isCreateAndDrop;
	}

	public void init() {
		Settings settings = Settings.getInstance();
		String mode = settings.value("jdbc.mode");
		log.info("Execute database operations using \"" + mode + "\" mode");
		log.info("Started creating schema...");
		
		boolean isH2DB = settings.value("jdbc.url").contains("h2");
		
		try {
			switch (mode.toLowerCase()) {
				case "update":
					populateWithData();
					break;
				case "create":
					if (!isH2DB) {
						dropDatabase();					
						createDatabase();
					}
					createSchema();
					populateWithData();
					break;
				case "create-drop":
					isCreateAndDrop = true;
					if (!isH2DB) {
						dropDatabase();
						createDatabase();
					}
					createSchema();
					break;
			}
			
			log.info("Schema was successfully created|Data was persisted");
		} catch (Exception e) {
			log.error("Schema creation was failed|Data persistence was failed!", e);
			System.exit(-1);
		}
		
		done = true;
	}
	
	public void createSchema() throws Exception {
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
			log.error("Error while creating database schema", e);
			try { connection.rollback(); } catch (SQLException ex) { }
			throw e;
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	private void createDatabase() throws Exception {
		Connection connection = JdbcConnection.getConnection("");
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS schedule");
		} catch (SQLException e) {
			log.error("Couldn't create database", e);
			throw e;
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	public void dropDatabase() throws Exception {
		Connection connection = JdbcConnection.getConnection("");
		Statement statement = null;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate("DROP DATABASE IF EXISTS schedule");
		} catch (SQLException e) {
			log.error("Couldn't drop database", e);
			throw e;
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
	
	private void populateWithData() throws Exception {
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
			log.error("Couldn't populate database with given data", e);
			try { connection.rollback(); } catch (SQLException ex) {}
			throw e;
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
	
	private void dropTables() throws Exception {
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
			log.error("Couldn't drop tables", e);
			try { connection.rollback(); } catch (SQLException ex) {}
			throw e;
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
