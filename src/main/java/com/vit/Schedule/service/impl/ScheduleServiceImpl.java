package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.model.Subject;
import com.vit.Schedule.model.Teacher;
import com.vit.Schedule.service.DayService;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.MajorService;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.SubjectService;
import com.vit.Schedule.service.TeacherService;
import com.vit.Schedule.util.DbUtils;
import com.vit.Schedule.util.JdbcConnection;

public class ScheduleServiceImpl implements ScheduleService {

	@Override
	public List<Schedule> findAllByGroup(Group group) {
		List<Schedule> schedules = new ArrayList<>();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM schedule WHERE group_id=(?)";
		SubjectService subjectService = new SubjectServiceImpl();
		DayService dayService = new DayServiceImpl();
		MajorService majorService = new MajorServiceImpl();
		TeacherService teacherService = new TeacherServiceImpl();
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, group.getId());
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Schedule schedule = new Schedule();
				schedule.setId(resultSet.getInt("id"));
				schedule.setSubject(subjectService.findById(resultSet.getInt("subject")));
				schedule.setDay(dayService.findByOrderNum(resultSet.getInt("day_num")));
				schedule.setWeek(resultSet.getString("week"));
				schedule.setSubject_num(resultSet.getInt("subject_num"));
				schedule.setMajor(majorService.findById(resultSet.getInt("major")));
				schedule.setGroup(group);
				schedule.setTeacher(teacherService.findById(resultSet.getInt("teacher")));
				schedule.setLessonType(resultSet.getString("lesson_type"));
				schedule.setClassroom(resultSet.getString("classroom"));
				schedules.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return schedules;
	}

	@Override
	public Schedule findById(int id) {
		Schedule schedule = new Schedule();
		
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM schedule WHERE id=(?)";
		
		SubjectService subjectService = new SubjectServiceImpl();
		DayService dayService = new DayServiceImpl();
		MajorService majorService = new MajorServiceImpl();
		TeacherService teacherService = new TeacherServiceImpl();
		GroupService groupService = new GroupServiceImpl();
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				schedule.setId(resultSet.getInt("id"));
				schedule.setSubject(subjectService.findById(resultSet.getInt("subject")));
				schedule.setDay(dayService.findByOrderNum(resultSet.getInt("day_num")));
				schedule.setWeek(resultSet.getString("week"));
				schedule.setSubject_num(resultSet.getInt("subject_num"));
				schedule.setMajor(majorService.findById(resultSet.getInt("major")));
				schedule.setGroup(groupService.findById(resultSet.getInt("group_id")));
				schedule.setTeacher(teacherService.findById(resultSet.getInt("teacher")));
				schedule.setLessonType(resultSet.getString("lesson_type"));
				schedule.setClassroom(resultSet.getString("classroom"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		
		return schedule;
	}

	@Override
	public void updateSubject(Subject subject, Schedule schedule) {
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		String sql = "UPDATE schedule SET subject=(?) WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, subject.getId());
			statement.setInt(2, schedule.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}

	@Override
	public void updateLessonType(String lessonType, Schedule schedule) {
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		String sql = "UPDATE schedule SET lesson_type=(?) WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, lessonType);
			statement.setInt(2, schedule.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}

	@Override
	public void updateTeacher(Teacher teacher, Schedule schedule) {
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		String sql = "UPDATE schedule SET teacher=(?) WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			if (teacher != null) {
				statement.setInt(1, teacher.getId());
			} else {
				statement.setNull(1, Types.INTEGER);
			}
			
			statement.setInt(2, schedule.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}

	@Override
	public void updateClassroom(String classroom, Schedule schedule) {
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		String sql = "UPDATE schedule SET classroom=(?) WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, classroom);
			statement.setInt(2, schedule.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}

	@Override
	public void delete(int id) {
		Connection connection = JdbcConnection.getConnection();
		PreparedStatement statement = null;
		String sql = "DELETE FROM schedule WHERE id=(?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
	}
}
