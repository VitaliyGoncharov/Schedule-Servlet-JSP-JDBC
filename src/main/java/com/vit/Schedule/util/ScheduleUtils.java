package com.vit.Schedule.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vit.Schedule.model.Day;
import com.vit.Schedule.model.Schedule;
import com.vit.Schedule.model.Subject;
import com.vit.Schedule.model.Teacher;

public class ScheduleUtils {
	
	private static enum WEEK {
		UP(1),
		DOWN(2);
		
		private final int order;
		
		WEEK(int order) {
			this.order = order;
		}
		
		public int getOrder() {
			return this.order;
		}
	}
	
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
			
			subjectMap.put("scheduleId", String.valueOf(schedule.getId()));
			subjectMap.put("subject", subject.getTitle());
			subjectMap.put("lessonType", schedule.getLessonType());
			subjectMap.put("classroom", schedule.getClassroom());
			
			upAndDownSubjects.put(schedule.getWeek(), subjectMap);
			dayMap.put(subjectNum, upAndDownSubjects);
			daysMap.put(day, dayMap);
		}
		
		return daysMap;
	}
	
	public static Map<Day, List<Schedule>> mapToDays2(List<Schedule> schedules) {
		Map<Day, List<Schedule>> daysMap = new HashMap<>();
		
		for (Schedule schedule : schedules) {
			Day day = schedule.getDay();
			
			List<Schedule> daySchedules = daysMap.get(day);
			if (daySchedules == null) {
				daySchedules = new ArrayList<>();
			}
			daySchedules.add(schedule);
			daysMap.put(day, daySchedules);
		}
		
		return daysMap;
	}
	
	private static class DayComparator1 implements Comparator<Entry<Day, Map<Integer, Map<String, Map<String,String>>>>> {

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
	
	private static class DayComparator2 implements Comparator<Entry<Day, List<Schedule>>> {

		@Override
		public int compare(Entry<Day, List<Schedule>> o1,
				Entry<Day, List<Schedule>> o2) {
			int day1OrderNum = o1.getKey().getOrderNum();
			int day2OrderNum = o2.getKey().getOrderNum();
			
			if (day1OrderNum > day2OrderNum) return 1;
			if (day1OrderNum == day2OrderNum) return 0;
			return -1;
		}
	}
	
	private static class LessonComparator implements Comparator<Schedule> {
		@Override
		public int compare(Schedule obj1, Schedule obj2) {
			int lessonNum1 = obj1.getSubject_num();
			int lessonNum2 = obj2.getSubject_num();
			
			if (lessonNum1 > lessonNum2) return 1;
			if (lessonNum1 == lessonNum2) return 0;
			return -1;
		}
	}
	
	private static class WeekComparator implements Comparator<Schedule> {
		@Override
		public int compare(Schedule obj1, Schedule obj2) {
			int lessonNum1 = obj1.getSubject_num();
			int lessonNum2 = obj2.getSubject_num();
			
			String week1 = obj1.getWeek();
			String week2 = obj2.getWeek();
			int week1Order = WEEK.valueOf(week1.toUpperCase()).getOrder();
			int week2Order = WEEK.valueOf(week2.toUpperCase()).getOrder();
			
			if (lessonNum1 != lessonNum2) return 0;
			if (week1Order > week2Order) return 1;
			return -1;
		}
	}
	
	/*
	 * Sort ScheduleMap by days
	 * 
	 * @param daysMap unsorted schedule map
	 * @return sortedDaysMap
	 */
	public static Map<Day, Map<Integer, Map<String, Map<String,String>>>> sortByDay1(Map<Day, Map<Integer, Map<String, Map<String,String>>>> daysMap) {
		List<Entry<Day, Map<Integer, Map<String, Map<String,String>>>>> daysEntriesList = new ArrayList<>(daysMap.entrySet());
		
		Collections.sort(daysEntriesList, new DayComparator1());
		
		Map<Day, Map<Integer, Map<String, Map<String,String>>>> sortedDaysMap = new LinkedHashMap<>();
		for (Entry<Day, Map<Integer, Map<String, Map<String,String>>>> daysMapEntry : daysEntriesList) {
			sortedDaysMap.put(daysMapEntry.getKey(), daysMapEntry.getValue());
		}
		return sortedDaysMap;
	}
	
	/*
	 * Sort ScheduleMap by days
	 * 
	 * @param daysMap unsorted schedule map
	 * @return sortedDaysMap
	 */
	public static Map<Day, List<Schedule>> sortByDay2(Map<Day, List<Schedule>> daysMap) {
		List<Entry<Day, List<Schedule>>> daysEntriesList = new ArrayList<>(daysMap.entrySet());
		
		Collections.sort(daysEntriesList, new DayComparator2());
		
		Map<Day, List<Schedule>> sortedDaysMap = new LinkedHashMap<>();
		for (Entry<Day, List<Schedule>> daysMapEntry : daysEntriesList) {
			sortedDaysMap.put(daysMapEntry.getKey(), daysMapEntry.getValue());
		}
		return sortedDaysMap;
	}
	
	/*
	 * Sort ScheduleMap by days
	 * 
	 * @param daysMap unsorted schedule map
	 * @return sortedDaysMap
	 */
	public static Map<Day, List<Schedule>> sortByLesson(Map<Day, List<Schedule>> daysMap) {
		List<Entry<Day, List<Schedule>>> daysEntriesList = new ArrayList<>(daysMap.entrySet());
		
		Map<Day, List<Schedule>> sortedDaysMap = new LinkedHashMap<>();
		for (Entry<Day, List<Schedule>> dayEntry : daysEntriesList) {
			List<Schedule> daySchedules = dayEntry.getValue();
			Collections.sort(daySchedules, new LessonComparator());
			sortedDaysMap.put(dayEntry.getKey(), daySchedules);
		}
		
		return sortedDaysMap;
	}
	
	/*
	 * Sort ScheduleMap by days
	 * 
	 * @param daysMap unsorted schedule map
	 * @return sortedDaysMap
	 */
	public static Map<Day, List<Schedule>> sortByWeek(Map<Day, List<Schedule>> daysMap) {
		List<Entry<Day, List<Schedule>>> daysEntriesList = new ArrayList<>(daysMap.entrySet());
		
		Map<Day, List<Schedule>> sortedDaysMap = new LinkedHashMap<>();
		for (Entry<Day, List<Schedule>> dayEntry : daysEntriesList) {
			List<Schedule> daySchedules = dayEntry.getValue();
			Collections.sort(daySchedules, new WeekComparator());
			sortedDaysMap.put(dayEntry.getKey(), daySchedules);
		}
		
		return sortedDaysMap;
	}
	
	public static Map<Day, List<Schedule>> addEmptyDays(Map<Day, List<Schedule>> daysMap, List<Day> days) {
		int[] daysNums = {1, 2, 3, 4, 5, 6 };
		for (int i = 0; i < daysNums.length; i++) {
			int curDayNum = daysNums[i];
			
			boolean dayExists = daysMap.entrySet().stream()
				.filter(x -> {
					return x.getKey().getOrderNum() == curDayNum;
				})
				.findFirst().isPresent();
			
			if (!dayExists) {
				Day day = days.stream()
					.filter(x -> {
						return x.getOrderNum() == curDayNum;
					})
					.findFirst().get();
				
				daysMap.put(day, new ArrayList<>());
			}
		}
		return daysMap;
	}
}
