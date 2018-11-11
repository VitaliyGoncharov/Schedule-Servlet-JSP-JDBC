package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.Major;
import com.vit.Schedule.model.School;

public interface MajorService {
	List<Major> findAllBySchool(School school);
	Major findByUrl(String majorUrl);
	Major findById(int id);
	
	void add(Major major);
}
