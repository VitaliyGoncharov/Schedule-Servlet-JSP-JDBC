package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.model.Subject;

public interface SubjectService {
	Subject findById(int id);
	List<Subject> findAll();
}
