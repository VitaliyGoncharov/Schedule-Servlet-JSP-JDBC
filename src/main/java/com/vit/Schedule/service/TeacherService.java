package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.Teacher;

public interface TeacherService {
	Teacher findById(int id);
	List<Teacher> findAll();
}
