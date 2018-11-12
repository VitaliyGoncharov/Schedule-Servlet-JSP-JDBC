package com.vit.Schedule.servlet.group;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Major;
import com.vit.Schedule.service.GroupService;
import com.vit.Schedule.service.MajorService;
import com.vit.Schedule.service.impl.GroupServiceImpl;
import com.vit.Schedule.service.impl.MajorServiceImpl;
import static com.vit.Schedule.util.ServletUtils.sendResponse;

@WebServlet("/api/group/add")
public class GroupAddServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7287222647169052907L;
	private static Logger log = Logger.getLogger(GroupAddServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String groupTitle = request.getParameter("groupTitle");
		int courseNum = Integer.parseInt(request.getParameter("courseNum"));
		int majorId = Integer.parseInt(request.getParameter("majorId"));
		
		MajorService majorService = new MajorServiceImpl();
		Major major = majorService.findById(majorId);
		
		if (major == null) {
			sendResponse(500, "Major wasn't found!", response);
			return;
		}
		
		Group group = new Group();
		group.setTitle(groupTitle);
		group.setCourseNum(courseNum);
		group.setMajor(major);
		
		GroupService groupService = new GroupServiceImpl();
		try {
			groupService.add(group);
		} catch(Exception e) {
			sendResponse(500, "Error while adding new group!", response);
			return;
		}
		
		sendResponse(200, "Group was successfully added!", response);
	}
}
