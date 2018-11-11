package com.vit.Schedule.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vit.Schedule.model.Major;
import com.vit.Schedule.model.School;
import com.vit.Schedule.service.impl.MajorServiceImpl;
import com.vit.Schedule.service.impl.SchoolServiceImpl;

@WebServlet("/SchoolServlet")
public class SchoolServlet extends HttpServlet {
	private static final long serialVersionUID = 4823787713227185510L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String schoolUrl = (String) request.getAttribute("school");
		SchoolServiceImpl schoolService = new SchoolServiceImpl();
		School school = schoolService.findByUrl(schoolUrl);
		
		List<Major> majors = null;
		try {
			MajorServiceImpl majorService = new MajorServiceImpl(); 
			majors = majorService.findAllBySchool(school);
		} catch (NullPointerException e) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(
			          "/");
	        dispatcher.forward(request, response);
	        return;
		}
		
		request.setAttribute("majors", majors);
		request.setAttribute("schoolId", school.getId());
		RequestDispatcher dispatcher = request.getRequestDispatcher(
		          "/WEB-INF/views/school.jsp");
        dispatcher.forward(request, response);
	}
}
