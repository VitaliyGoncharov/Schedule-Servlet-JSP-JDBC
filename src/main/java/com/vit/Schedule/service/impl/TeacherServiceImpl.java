package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vit.Schedule.model.Teacher;
import com.vit.Schedule.service.TeacherService;
import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.JdbcConnection;

public class TeacherServiceImpl implements TeacherService {
	
	@Override
	public Teacher findById(int id) {
		Teacher teacher = new Teacher();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM teacher WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				teacher.setId(resultSet.getInt("id"));
				teacher.setFirstname(resultSet.getString("firstname"));
				teacher.setLastname(resultSet.getString("lastname"));
				teacher.setMiddlename(resultSet.getString("middlename"));
				teacher.setEmail(resultSet.getString("mail"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return teacher;
	}
}
