package com.vit.Schedule.servlet.schedule;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vit.Schedule.model.Day;
import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Major;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.model.Subject;
import com.vit.Schedule.model.Teacher;
import com.vit.Schedule.response.Response;
import com.vit.Schedule.service.DayService;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.SubjectService;
import com.vit.Schedule.service.TeacherService;
import com.vit.Schedule.service.impl.DayServiceImpl;
import com.vit.Schedule.service.impl.GroupServiceImpl;
import com.vit.Schedule.service.impl.ScheduleServiceImpl;
import com.vit.Schedule.service.impl.SubjectServiceImpl;
import com.vit.Schedule.service.impl.TeacherServiceImpl;

@WebServlet("/api/schedule/add")
public class ScheduleAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4636038876867086040L;
	private static Logger log = Logger.getLogger(ScheduleAddServlet.class);
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		int groupId = Integer.parseInt(request.getParameter("group"));
		
		int dayOrderNum = Integer.parseInt(request.getParameter("day"));
		int week = Integer.parseInt(request.getParameter("week"));
		int lessonNum = Integer.parseInt(request.getParameter("lessonNum"));
		int subjectId = Integer.parseInt(request.getParameter("subject"));
		int lessonType = Integer.parseInt(request.getParameter("lessonType"));
		int teacherId = Integer.parseInt(request.getParameter("teacher"));
		String classroom = request.getParameter("classroom");
		String weekStr = null;
		String lessonTypeStr = null;
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			log.error("Failed to get writer" + e);
			return;
		}
		
		DayService dayService = new DayServiceImpl();
		Day day = dayService.findByOrderNum(dayOrderNum);
		// check day order number | Required
		if (day == null || dayOrderNum < 1 || dayOrderNum > 7) {
			sendResponse(500, "Day number must be from 1 to 7", out);
			return;
		}
		
		// check week type | Required
		if (week != 1 && week != 2) {
			sendResponse(500, "Week can be set only by 1(up) and 2(down)", out);
			return;
		}
		
		// because I save week to database in string format
		if (week == 1) weekStr = "up";
		if (week == 2) weekStr = "down";
		
		// check lesson order number | Required
		if (lessonNum > 7 || lessonNum < 1) {
			sendResponse(500, "Lesson number must be from 1 to 7", out);
			return;
		}
		
		// get subject | Required
		SubjectService subjectService = new SubjectServiceImpl();
		Subject subject = subjectService.findById(subjectId);
		
		if (subject == null) {
			sendResponse(500, "Subject can't be null", out);
			return;
		}
		
		// map lesson type to string
		if (lessonType == 1) lessonTypeStr = "Л";
		if (lessonType == 2) lessonTypeStr = "П";
		
		TeacherService teacherService = new TeacherServiceImpl();
		Teacher teacher = teacherService.findById(teacherId);
		
		GroupService groupService = new GroupServiceImpl();
		Group group = groupService.findById(groupId);
		
		if (group == null) {
			sendResponse(500, "Group can't be null!", out);
			return;
		}
		
		Major major = group.getMajor();
		
		Schedule schedule = new Schedule();
		schedule.setDay(day);
		schedule.setSubject_num(lessonNum);
		schedule.setWeek(weekStr);
		schedule.setSubject(subject);
		schedule.setLessonType(lessonTypeStr);
		schedule.setTeacher(teacher);
		schedule.setClassroom(classroom);
		schedule.setGroup(group);
		schedule.setMajor(major);
		
		ScheduleService scheduleService = new ScheduleServiceImpl();
		scheduleService.add(schedule);
		sendResponse(200, "Schedule was successfully added!", out);
	}
	
	private void sendResponse(int status, String msg, PrintWriter out) {
		Response customResponse = new Response();
		customResponse.setStatus(status);
		customResponse.setMessage(msg);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			out.append(objectMapper.writeValueAsString(customResponse));
		} catch (JsonProcessingException e) {
			log.error("Couldn't generate JSON" + e);
		}
		out.flush();
	}
}
