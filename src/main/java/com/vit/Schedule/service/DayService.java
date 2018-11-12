package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.Day;

public interface DayService {
	Day findById(int id);
	Day findByOrderNum(int orderNum);
	List<Day> findAll();
}
