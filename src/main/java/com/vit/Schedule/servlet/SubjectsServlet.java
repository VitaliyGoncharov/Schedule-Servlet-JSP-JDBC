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
import com.vit.Schedule.model.Subject;
import com.vit.Schedule.service.SubjectService;
import com.vit.Schedule.service.impl.SubjectServiceImpl;

@WebServlet("/api/subjects")
public class SubjectsServlet extends HttpServlet {
	private static final long serialVersionUID = 4334796079896885173L;
	private static Logger log = Logger.getLogger(SubjectsServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		SubjectService subjectService = new SubjectServiceImpl();
		List<Subject> subjects = subjectService.findAll();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			PrintWriter out = response.getWriter();
			out.print(objectMapper.writeValueAsString(subjects));
			out.flush();
		} catch (IOException e) {
			log.error("Failed to write object to output", e);
		}
	}
}
