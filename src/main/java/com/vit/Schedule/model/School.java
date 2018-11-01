package com.vit.Schedule.model;

public class School {
	private int id;
	private String title;
	private String url;
	
	public School() {
	}
	
	public School(int id, String title, String url) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
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

	@Override
	public String toString() {
		return "School [id=" + id + ", title=" + title + ", url=" + url + "]";
	}
}
