package com.vit.Schedule.servlet.schedule;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.model.Subject;
import com.vit.Schedule.model.Teacher;
import com.vit.Schedule.response.Response;
import com.vit.Schedule.service.ScheduleService;
import com.vit.Schedule.service.SubjectService;
import com.vit.Schedule.service.TeacherService;
import com.vit.Schedule.service.impl.ScheduleServiceImpl;
import com.vit.Schedule.service.impl.SubjectServiceImpl;
import com.vit.Schedule.service.impl.TeacherServiceImpl;

@WebServlet("/api/schedule/edit")
public class ScheduleEditServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7427970768923629173L;
	private static Logger log = Logger.getLogger(ScheduleEditServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
		
		// get new subjectId (if it wasn't changed, get current subjectId)
		int subjectId = Integer.parseInt(request.getParameter("subjectId"));
		int lessonType = Integer.parseInt(request.getParameter("lessonType"));
		int teacherId = Integer.parseInt(request.getParameter("teacherId"));
		String classroom = request.getParameter("classroom");
		
		ScheduleService scheduleService = new ScheduleServiceImpl();
		Schedule schedule = scheduleService.findById(scheduleId);
		
		SubjectService subjectService = new SubjectServiceImpl();
		Subject subject = subjectService.findById(subjectId);
		
		TeacherService teacherService = new TeacherServiceImpl();
		Teacher teacher = teacherService.findById(teacherId);
		
		if (schedule != null) {
			if (subject != null) {
				scheduleService.updateSubject(subject, schedule);
			}
			
			switch (lessonType) {
				case 1: {
					scheduleService.updateLessonType("Л", schedule);
					break;
				}
				case 2: {
					scheduleService.updateLessonType("П", schedule);
					break;
				}
				default: {
					scheduleService.updateLessonType(null, schedule);
					break;
				}
			}
			if (teacher.getId() != 0) {
				scheduleService.updateTeacher(teacher, schedule);
			} else {
				scheduleService.updateTeacher(null, schedule);
			}
			scheduleService.updateClassroom(classroom, schedule);
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		Response customResponse = new Response();
		customResponse.setStatus(200);
		customResponse.setMessage("Schedule was updated");
		
		try {
			PrintWriter out = response.getWriter();
			out.print(objectMapper.writeValueAsString(customResponse));
			out.flush();
		} catch (IOException e) {
			log.error("Failed to get writer" + e);
		}
		
	}
}
