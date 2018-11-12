package com.vit.Schedule.servlet.major;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Major;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.MajorService;
import com.vit.Schedule.service.impl.GroupServiceImpl;
import com.vit.Schedule.service.impl.MajorServiceImpl;

@WebServlet("/MajorServlet")
public class MajorServlet extends HttpServlet {
	private static final long serialVersionUID = 8075784912817567814L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String majorUrl = (String) request.getAttribute("major");
		
		MajorService majorService = new MajorServiceImpl();
		Major major = majorService.findByUrl(majorUrl);
		
		GroupService groupService = new GroupServiceImpl(); 
		List<Group> groups = groupService.findAllByMajor(major);
		
		Map<Integer, List<Group>> courseToGroupsMap = new HashMap<>();
		for (int i = 1; i < major.getDuration()+1; i++) {
			int innerI = i;
			List<Group> distinctGroups = groups.stream()
					.filter(x -> innerI == x.getCourseNum())
					.collect(Collectors.toList());
			if (!distinctGroups.isEmpty()) {
				courseToGroupsMap.put(innerI, distinctGroups);
			}
		}
		request.setAttribute("courseToGroupsMap", courseToGroupsMap);
		request.setAttribute("majorId", major.getId());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/major.jsp");
		dispatcher.forward(request, response);
	}
}
