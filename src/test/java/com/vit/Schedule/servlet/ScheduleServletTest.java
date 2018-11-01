package com.vit.Schedule.servlet;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.vit.Schedule.model.Day;
import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.impl.GroupServiceImpl;
import com.vit.Schedule.service.impl.ScheduleServiceImpl;
import com.vit.Schedule.util.ScheduleUtils;

public class ScheduleServletTest {

	@Test
	public void checkThatSchedulesAreMappedToDays() {
		GroupService groupService = new GroupServiceImpl();
		Group group = groupService.findById(1);
		
		ScheduleService scheduleService = new ScheduleServiceImpl();
		List<Schedule> schedules = scheduleService.findAllByGroup(group);
		Map<Day, Map<Integer, Map<String,String>>> schedulesByDay = ScheduleUtils.mapToDays(schedules);
		
		assertTrue(!schedulesByDay.isEmpty());
	}
}
