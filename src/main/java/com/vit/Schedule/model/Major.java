package com.vit.Schedule.model;

public class Major {
	private int id;
	private String title;
	private String url;
	private int duration;
	private School school;
	
	public Major() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}

	@Override
	public String toString() {
		return "Major [id=" + id + ", title=" + title + ", url=" + url + ", duration=" + duration + ", school=" + school
				+ "]";
	}
}
