package com.vit.Schedule.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class Settings {
	private static final Settings INSTANCE = new Settings();
	
	private final Properties properties = new Properties();
	
	private Settings() {
		try {
			properties.load(new FileInputStream(this.getClass().getClassLoader().getResource("project.properties").getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Settings getInstance() { return INSTANCE; }
	public String value(String key) { return properties.getProperty(key); }
}
