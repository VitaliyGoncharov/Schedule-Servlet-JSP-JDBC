package com.vit.Schedule.servlet.major;

import static com.vit.Schedule.util.ServletUtils.sendResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.vit.Schedule.model.Major;
import com.vit.Schedule.model.School;
import com.vit.Schedule.service.MajorService;
import com.vit.Schedule.service.SchoolService;
import com.vit.Schedule.service.impl.MajorServiceImpl;
import com.vit.Schedule.service.impl.SchoolServiceImpl;
import com.vit.Schedule.util.MajorUtils;

@WebServlet("/api/major/add")
public class MajorAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5766379757761231751L;
	private static Logger log = Logger.getLogger(MajorAddServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		int schoolId = Integer.parseInt(request.getParameter("school"));
		String majorTitle = request.getParameter("major");
		int duration = Integer.parseInt(request.getParameter("duration"));
		String url = MajorUtils.generateUrl(majorTitle);
		
		if (schoolId < 1) {
			sendResponse(500, "School id can't be less than 0!", response);
		}
		
		SchoolService schoolService = new SchoolServiceImpl();
		School school = schoolService.findById(schoolId);
		
		MajorService majorService = new MajorServiceImpl();
		Major major = new Major();
		major.setSchool(school);
		major.setTitle(majorTitle);
		major.setUrl(url);
		major.setDuration(duration);
		majorService.add(major);
		
		sendResponse(200, "Major was successfully added!", response);
	}
}
