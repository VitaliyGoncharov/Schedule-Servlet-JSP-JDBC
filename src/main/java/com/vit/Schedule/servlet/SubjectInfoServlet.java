package com.vit.Schedule.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(urlPatterns = "/subject/info")
public class SubjectInfoServlet extends HttpServlet {
	private static final long serialVersionUID = -6309679489747593981L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		
		Map<String, String> subjectMap = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			PrintWriter out = response.getWriter();
			out.print(objectMapper.writeValueAsString(subjectMap));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
