package com.vit.Schedule.servlet;

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

import org.apache.log4j.Logger;

import com.vit.Schedule.model.Day;
import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.impl.GroupServiceImpl;
import com.vit.Schedule.service.impl.ScheduleServiceImpl;
import com.vit.Schedule.util.ScheduleUtils;

@WebServlet("/schedule/edit/*")
public class ScheduleEditPageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3856471666504763119L;
	private static Logger log = Logger.getLogger(ScheduleEditPageServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getPathInfo();
		String[] urlParts = url.split("/");
		int groupId;
		try {
			groupId = Integer.parseInt(urlParts[urlParts.length-1]);	
		} catch (NumberFormatException e) {
			log.error("Incorrect format of groupId in url" + e);
			return;
		}
		
		GroupService groupService = new GroupServiceImpl();
		Group group = groupService.findById(groupId);
		
		ScheduleService scheduleService = new ScheduleServiceImpl();
		List<Schedule> schedules = scheduleService.findAllByGroup(group);
		
		Map<Day, List<Schedule>> mappedSchedules = ScheduleUtils.mapToDays2(schedules);
		mappedSchedules = ScheduleUtils.sortByDay2(mappedSchedules);
		mappedSchedules = ScheduleUtils.sortByLesson(mappedSchedules);
		mappedSchedules = ScheduleUtils.sortByWeek(mappedSchedules);
		request.setAttribute("schedules", mappedSchedules);
		
		Map<Integer, String> bellSchedule = new HashMap<>();
		bellSchedule.put(1, "8.30-10.00");
		bellSchedule.put(2, "10.10-11.40");
		bellSchedule.put(3, "11.50-13.20");
		bellSchedule.put(4, "13.30-15.00");
		bellSchedule.put(5, "15.10-16.40");
		bellSchedule.put(6, "16.50-18.20");
		
		request.setAttribute("bellSchedule", bellSchedule);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/scheduleEditPage.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}
}
