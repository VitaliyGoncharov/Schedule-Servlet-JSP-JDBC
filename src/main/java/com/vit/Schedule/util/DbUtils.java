package com.vit.Schedule.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Example from
 * https://stackoverflow.com/questions/2225221/closing-database-connections-in-java
 * 
 * @author VitaliyG
 *
 */
public class DbUtils {
	
	/**
	 * Can throw NullPointerException and SQLException
	 * @param stmt
	 */
	public static void closeQuietly(Statement stmt) {
		try { stmt.close(); } catch (Exception e) {}
	}
	
	/**
	 * Can throw NullPointerException and SQLException
	 * @param rs
	 */
	public static void closeQuietly(ResultSet rs) {
		try { rs.close(); } catch (Exception e) {}
	}
	
	/**
	 * Can throw NullPointerException and SQLException
	 * @param conn
	 */
	public static void closeQuietly(Connection conn) {
		try { conn.close(); } catch (Exception e) {}
	}
}
