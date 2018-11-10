package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vit.Schedule.model.School;
import com.vit.Schedule.service.SchoolService;
import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.JdbcConnection;

public class SchoolServiceImpl implements SchoolService {
	private static Logger log = Logger.getLogger(SchoolServiceImpl.class);
	
	public SchoolServiceImpl() {
	}
	
	@Override
	public List<School> findAll() {
		List<School> schools = new ArrayList<>();
		
		String sql = "SELECT * FROM school";
		Connection connection = JdbcConnection.getConnection();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				School school = new School();
				school.setId(resultSet.getInt("id"));
				school.setTitle(resultSet.getString("title"));
				school.setUrl(resultSet.getString("url"));
				schools.add(school);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return schools;
	}
	
	@Override
	public School findByUrl(String url) {
		School school = new School();
		
		String sql = "SELECT * FROM school WHERE url=(?)";
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		
		try {			
			prepStatement = connection.prepareStatement(sql);
			prepStatement.setString(1, url);
			resultSet = prepStatement.executeQuery();
			if (resultSet.next()) {
				school.setId(resultSet.getInt("id"));
				school.setTitle(resultSet.getString("title"));
				school.setUrl(resultSet.getString("url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(prepStatement);
			DbUtils.closeQuietly(connection);
		}
		
		return school;
	}
	
	@Override
	public School findById(int id) {
		School school = new School();
		
		String sql = "SELECT * FROM school WHERE id=(?)";
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		
		try {			
			prepStatement = connection.prepareStatement(sql);
			prepStatement.setInt(1, id);
			resultSet = prepStatement.executeQuery();
			if (resultSet.next()) {
				school.setId(resultSet.getInt("id"));
				school.setTitle(resultSet.getString("title"));
				school.setUrl(resultSet.getString("url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(prepStatement);
			DbUtils.closeQuietly(connection);
		}
		
		return school;
	}
}
