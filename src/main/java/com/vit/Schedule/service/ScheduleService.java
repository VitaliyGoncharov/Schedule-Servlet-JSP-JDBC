package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.model.Subject;
import com.vit.Schedule.model.Teacher;

public interface ScheduleService {
	Schedule findById(int id);
	List<Schedule> findAllByGroup(Group group);
	
	void updateSubject(Subject subject, Schedule schedule);
	void updateLessonType(String lessonType, Schedule schedule);
	void updateTeacher(Teacher teacher, Schedule schedule);
	void updateClassroom(String classroom, Schedule schedule);
	
	void delete(int id);
	void add(Schedule schedule);
}
