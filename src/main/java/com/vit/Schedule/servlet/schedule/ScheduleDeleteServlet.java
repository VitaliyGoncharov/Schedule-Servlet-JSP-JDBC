package com.vit.Schedule.servlet.schedule;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vit.Schedule.response.Response;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.impl.ScheduleServiceImpl;

@WebServlet("/api/schedule/delete")
public class ScheduleDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7016951446590112341L;
	private static Logger log = Logger.getLogger(ScheduleDeleteServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		int scheduleId = Integer.parseInt(request.getParameter("id"));
		
		ScheduleService scheduleService = new ScheduleServiceImpl();
		scheduleService.delete(scheduleId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		Response customResponse = new Response();
		customResponse.setStatus(200);
		customResponse.setMessage("Schedule was deleted");
		
		try {
			PrintWriter out = response.getWriter();
			out.println(objectMapper.writeValueAsString(customResponse));
			out.flush();
		} catch (IOException e) {
			log.error("Failed to get writer" + e);
		}
	}
}
