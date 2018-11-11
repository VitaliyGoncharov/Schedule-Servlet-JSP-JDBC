package com.vit.Schedule.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vit.Schedule.response.Response;

public class ServletUtils {
	private static Logger log = Logger.getLogger(ServletUtils.class);
	
	public static void sendResponse(int status, String msg, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			log.error("Failed to get writer" + e);
			return;
		}
		
		Response customResponse = new Response();
		customResponse.setStatus(status);
		customResponse.setMessage(msg);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			out.append(objectMapper.writeValueAsString(customResponse));
		} catch (JsonProcessingException e) {
			log.error("Couldn't generate JSON" + e);
		}
		out.flush();
	}
}
