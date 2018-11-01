package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vit.Schedule.model.Day;
import com.vit.Schedule.service.DayService;
import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.JdbcConnection;

public class DayServiceImpl implements DayService {

	@Override
	public Day findById(int id) {
		Day day = new Day();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM day WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				day.setId(resultSet.getInt("id"));
				day.setName(resultSet.getString("name"));
				day.setOrderNum(resultSet.getInt("order_num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return day;
	}
	
	@Override
	public Day findByOrderNum(int orderNum) {
		Day day = new Day();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM day WHERE order_num=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, orderNum);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				day.setId(resultSet.getInt("id"));
				day.setName(resultSet.getString("name"));
				day.setOrderNum(resultSet.getInt("order_num"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return day;
	}
}
