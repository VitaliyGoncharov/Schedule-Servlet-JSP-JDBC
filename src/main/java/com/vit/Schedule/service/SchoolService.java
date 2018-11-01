package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.School;

public interface SchoolService {
	List<School> findAll();
	School findByUrl(String schoolUrl);
	School findById(int id);
}
