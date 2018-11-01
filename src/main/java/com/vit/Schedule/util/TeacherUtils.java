package com.vit.Schedule.util;

import com.vit.Schedule.model.Teacher;

public class TeacherUtils {
	
	public static String prepareTeacherName(Teacher teacher) {
		if (teacher == null) {
			return null;
		}
		
		if (teacher.getFirstname() == null || teacher.getLastname() == null
				|| teacher.getMiddlename() == null) {
			return null;
		}
		
		return new StringBuilder()
				.append(teacher.getLastname())
				.append(" ")
				.append(teacher.getFirstname().charAt(0) + ".")
				.append(teacher.getMiddlename().charAt(0) + ".")
				.toString();
	}
}
