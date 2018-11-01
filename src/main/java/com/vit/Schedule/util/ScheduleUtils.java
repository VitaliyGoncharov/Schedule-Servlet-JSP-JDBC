package com.vit.Schedule.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.vit.Schedule.model.Day;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.model.Subject;
import com.vit.Schedule.model.Teacher;

public class ScheduleUtils {
	
	public static Map<Day, Map<Integer, Map<String, Map<String,String>>>> mapToDays(List<Schedule> schedules) {
		Map<Day, Map<Integer, Map<String, Map<String,String>>>> daysMap = new HashMap<>();
		
		for (Schedule schedule : schedules) {
			Day day = schedule.getDay();
			Subject subject = schedule.getSubject();
			Teacher teacher = schedule.getTeacher();
			int subjectNum = schedule.getSubject_num();
			
			Map<Integer, Map<String, Map<String,String>>> dayMap = daysMap.get(day);
			Map<String, Map<String,String>> upAndDownSubjects = null;
			Map<String,String> subjectMap = null;
			if (dayMap == null) {
				dayMap = new HashMap<>();
				upAndDownSubjects = new HashMap<>();
				subjectMap = new HashMap<>();
			} else {
				upAndDownSubjects = dayMap.get(subjectNum);
				if (upAndDownSubjects == null) {
					upAndDownSubjects = new HashMap<>();
					subjectMap = new HashMap<>();
				} else {
					subjectMap = upAndDownSubjects.get(schedule.getWeek());
					if (subjectMap == null) subjectMap = new HashMap<>();
				}
			}
			
			String preparedTeacher;
			if ((preparedTeacher = TeacherUtils.prepareTeacherName(teacher)) != null) {
				subjectMap.put("teacher", preparedTeacher);
			}
			
			subjectMap.put("subject", subject.getTitle());
			subjectMap.put("lessonType", schedule.getLessonType());
			subjectMap.put("classroom", schedule.getClassroom());
			
			upAndDownSubjects.put(schedule.getWeek(), subjectMap);
			dayMap.put(subjectNum, upAndDownSubjects);
			daysMap.put(day, dayMap);
		}
		
		return daysMap;
	}
	
	private static class DayComparator implements Comparator<Entry<Day, Map<Integer, Map<String, Map<String,String>>>>> {

		@Override
		public int compare(Entry<Day, Map<Integer, Map<String, Map<String,String>>>> o1,
				Entry<Day, Map<Integer, Map<String, Map<String,String>>>> o2) {
			int day1OrderNum = o1.getKey().getOrderNum();
			int day2OrderNum = o2.getKey().getOrderNum();
			
			if (day1OrderNum > day2OrderNum) return 1;
			if (day1OrderNum == day2OrderNum) return 0;
			return -1;
		}
	}
	
	/*
	 * Sort ScheduleMap by days
	 * 
	 * @param daysMap unsorted schedule map
	 * @return sortedDaysMap
	 */
	public static Map<Day, Map<Integer, Map<String, Map<String,String>>>> sortByDay(Map<Day, Map<Integer, Map<String, Map<String,String>>>> daysMap) {
		List<Entry<Day, Map<Integer, Map<String, Map<String,String>>>>> daysEntriesList = new ArrayList<>(daysMap.entrySet());
		
		Collections.sort(daysEntriesList, new DayComparator());
		
		Map<Day, Map<Integer, Map<String, Map<String,String>>>> sortedDaysMap = new LinkedHashMap<>();
		for (Entry<Day, Map<Integer, Map<String, Map<String,String>>>> daysMapEntry : daysEntriesList) {
			sortedDaysMap.put(daysMapEntry.getKey(), daysMapEntry.getValue());
		}
		return sortedDaysMap;
	}
}
