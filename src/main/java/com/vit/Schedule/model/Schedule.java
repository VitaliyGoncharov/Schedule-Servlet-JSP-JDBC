package com.vit.Schedule.model;

public class Schedule {
	private int id;
	private Subject subject;
	private Day day;
	private String week;
	private int subject_num;
	private Major major;
	private Group group;
	private Teacher teacher;
	private String lessonType;
	private String classroom;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Day getDay() {
		return day;
	}
	public void setDay(Day day) {
		this.day = day;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getSubject_num() {
		return subject_num;
	}
	public void setSubject_num(int subject_num) {
		this.subject_num = subject_num;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getLessonType() {
		return lessonType;
	}
	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
}
