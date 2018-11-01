package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Schedule;

public interface ScheduleService {
	List<Schedule> findAllByGroup(Group group);
}
