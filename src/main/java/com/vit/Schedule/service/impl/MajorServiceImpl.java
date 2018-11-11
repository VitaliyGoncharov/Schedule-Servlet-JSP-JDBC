package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vit.Schedule.model.Major;
import com.vit.Schedule.model.School;
import com.vit.Schedule.service.MajorService;
import com.vit.Schedule.service.SchoolService;
import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.JdbcConnection;

public class MajorServiceImpl implements MajorService {
	private static Logger log = Logger.getLogger(MajorServiceImpl.class);
	
	public MajorServiceImpl() {
	}
	
	@Override
	public List<Major> findAllBySchool(School school) {
		log.info("Getting majors for school " + school.getTitle());
		int schoolId = school.getId();
		List<Major> majors = new ArrayList<>();
		
		String sql = "SELECT * FROM major WHERE school=(?)";
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		
		try {
			prepStatement = connection.prepareStatement(sql);
			prepStatement.setInt(1, schoolId);
			resultSet = prepStatement.executeQuery();
			
			while (resultSet.next()) {
				Major major = new Major();
				major.setId(resultSet.getInt("id"));
				major.setTitle(resultSet.getString("title"));
				major.setUrl(resultSet.getString("url"));
				major.setDuration(resultSet.getInt("duration"));
				major.setSchool(school);
				majors.add(major);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(prepStatement);
			DbUtils.closeQuietly(connection);
		}
		
		return majors;
	}
	
	@Override
	public Major findByUrl(String majorUrl) {
		Major major = new Major();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM major WHERE url=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, majorUrl);
			resultSet = statement.executeQuery();
			
			SchoolService schoolService = new SchoolServiceImpl();
			
			if (resultSet.next()) {
				major.setId(resultSet.getInt("id"));
				major.setTitle(resultSet.getString("title"));
				major.setDuration(resultSet.getInt("duration"));
				major.setUrl(resultSet.getString("url"));
				major.setSchool(schoolService.findById(resultSet.getInt("school")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return major;
	}
	
	@Override
	public Major findById(int id) {
		Major major = new Major();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM major WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			SchoolService schoolService = new SchoolServiceImpl();
			
			if (resultSet.next()) {
				major.setId(resultSet.getInt("id"));
				major.setTitle(resultSet.getString("title"));
				major.setDuration(resultSet.getInt("duration"));
				major.setUrl(resultSet.getString("url"));
				major.setSchool(schoolService.findById(resultSet.getInt("school")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return major;
	}

	@Override
	public void add(Major major) {
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		String sql = "INSERT INTO major (title,url,duration,school) VALUES (?,?,?,?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, major.getTitle());
			statement.setString(2, major.getUrl());
			statement.setInt(3, major.getDuration());
			statement.setInt(4, major.getSchool().getId());
			int insertedRows = statement.executeUpdate();
			
			if (insertedRows == 0) {
				throw new SQLException("Coudn't insert new major!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
}
