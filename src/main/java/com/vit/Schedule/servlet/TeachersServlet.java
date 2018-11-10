package com.vit.Schedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vit.Schedule.model.Teacher;
import com.vit.Schedule.service.TeacherService;
import com.vit.Schedule.service.impl.TeacherServiceImpl;

@WebServlet("/api/teachers")
public class TeachersServlet extends HttpServlet {
	private static final long serialVersionUID = -2446666872968378779L;
	private static Logger log = Logger.getLogger(TeachersServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		TeacherService teacherService = new TeacherServiceImpl();
		List<Teacher> teachers = teacherService.findAll();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			PrintWriter out = response.getWriter();
			out.print(objectMapper.writeValueAsString(teachers));
			out.flush();
		} catch (IOException e) {
			log.error("Failed to get writer" + e);
		}
		
	}
}
