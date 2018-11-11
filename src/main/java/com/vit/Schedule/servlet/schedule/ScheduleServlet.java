package com.vit.Schedule.servlet.schedule;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vit.Schedule.model.Day;
import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.impl.GroupServiceImpl;
import com.vit.Schedule.service.impl.ScheduleServiceImpl;
import com.vit.Schedule.util.ScheduleUtils;

@WebServlet(
	name = "ScheduleServlet",
	urlPatterns = "/schedule/*")
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1880295393595205856L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getPathInfo();
		String[] reqUrlParts = reqUrl.split("/");
		int groupId = Integer.parseInt(reqUrlParts[1]);
		
		GroupService groupService = new GroupServiceImpl();
		Group group = groupService.findById(groupId);
		
		ScheduleService scheduleService = new ScheduleServiceImpl();
		List<Schedule> schedules = scheduleService.findAllByGroup(group);
		Map<Day, Map<Integer, Map<String, Map<String,String>>>> schedulesByDays = ScheduleUtils.mapToDays(schedules);
		schedulesByDays = ScheduleUtils.sortByDay1(schedulesByDays);
		request.setAttribute("schedulesByDays", schedulesByDays);
		
		Map<Integer, String> bellSchedule = new HashMap<>();
		bellSchedule.put(1, "8.30-10.00");
		bellSchedule.put(2, "10.10-11.40");
		bellSchedule.put(3, "11.50-13.20");
		bellSchedule.put(4, "13.30-15.00");
		bellSchedule.put(5, "15.10-16.40");
		bellSchedule.put(6, "16.50-18.20");
		request.setAttribute("bellSchedule", bellSchedule);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/schedule.jsp");
		dispatcher.forward(request, response);
	}
}
