package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vit.Schedule.model.Subject;
import com.vit.Schedule.service.SubjectService;
import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.JdbcConnection;

public class SubjectServiceImpl implements SubjectService {

	@Override
	public Subject findById(int id) {
		Subject subject = new Subject();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM subject WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				subject.setId(resultSet.getInt("id"));
				subject.setTitle(resultSet.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		return subject;
	}
}
