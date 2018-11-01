package com.vit.Schedule.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.service.DayService;
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
}
