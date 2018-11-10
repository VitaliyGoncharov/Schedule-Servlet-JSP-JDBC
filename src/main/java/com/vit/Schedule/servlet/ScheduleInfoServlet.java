package com.vit.Schedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.impl.ScheduleServiceImpl;

@WebServlet(urlPatterns = "/api/schedule/info")
public class ScheduleInfoServlet extends HttpServlet {
	private static final long serialVersionUID = -6309679489747593981L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		int scheduleId = Integer.parseInt(request.getParameter("id"));
		
		ScheduleService scheduleService = new ScheduleServiceImpl();
		Schedule schedule = scheduleService.findById(scheduleId);
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			PrintWriter out = response.getWriter();
			out.print(objectMapper.writeValueAsString(schedule));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
