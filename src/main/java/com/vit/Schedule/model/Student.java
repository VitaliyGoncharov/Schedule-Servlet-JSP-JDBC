package com.vit.Schedule.model;

public class Student {
	private String name;
	private String major;
	
	public Student(String name, String major) {
		super();
		this.name = name;
		this.major = major;
	}

	public Student() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
}
