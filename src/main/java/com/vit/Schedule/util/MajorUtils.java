package com.vit.Schedule.util;

public class MajorUtils {
	public static String generateUrl(String title) {
		String titleInLowerCase = title.toLowerCase();
		return titleInLowerCase.replaceAll(" ", "_"); 
	}
}
