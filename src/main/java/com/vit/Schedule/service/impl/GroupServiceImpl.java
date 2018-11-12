package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Major;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.MajorService;
import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.JdbcConnection;

public class GroupServiceImpl implements GroupService {
	private static Logger log = Logger.getLogger(GroupServiceImpl.class);

	@Override
	public Group findById(int id) {
		Group group = new Group();

		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM groups WHERE id=(?)";

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			MajorService majorService = new MajorServiceImpl();

			while (resultSet.next()) {
				group.setId(resultSet.getInt("id"));
				group.setTitle(resultSet.getString("title"));
				group.setCourseNum(resultSet.getInt("course_num"));
				group.setMajor(majorService.findById(resultSet.getInt("major")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}

		return group;
	}

	@Override
	public List<Group> findAllByMajor(Major major) {
		List<Group> groups = new ArrayList<>();

		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM groups WHERE major=(?)";

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, major.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Group group = new Group();
				group.setId(resultSet.getInt("id"));
				group.setTitle(resultSet.getString("title"));
				group.setCourseNum(resultSet.getInt("course_num"));
				group.setMajor(major);
				groups.add(group);
			}
		} catch (SQLException e) {

		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}

		return groups;
	}

	@Override
	public void add(Group group) throws SQLException {
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		String sql = "INSERT INTO groups (title,course_num,major) VALUES (?,?,?)";

		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, group.getTitle());
			statement.setInt(2, group.getCourseNum());
			statement.setInt(3, group.getMajor().getId());
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted < 1) {
				throw new SQLException("Unable to insert new group");
			}
		} catch (SQLException e) {
			log.error("Error while adding new group" + e);
			throw e;
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
}
