package com.vit.Schedule.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		name = "SchoolDispatcherServlet",
		urlPatterns = "/school/*")
public class SchoolDispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = -4130548096845924037L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get path info and leave part after /ServletContext/
		String reqUrl = request.getPathInfo();
		
		if (reqUrl.startsWith("/")) {
			reqUrl = reqUrl.substring(1);
		}

		Map<String, String> urlsMap = new HashMap<>();
		urlsMap.put("{school}/{major}", "/MajorServlet");
		urlsMap.put("{school}", "/SchoolServlet");
		
		for (Entry<String,String> entry : urlsMap.entrySet()) {
			String prepPattern = entry.getKey();
			String[] prepPatternParts = prepPattern.split("/");
			List<String> patternVarsNames = new ArrayList<>();
			for (int i = 0; i < prepPatternParts.length; i++) {
				String part =  prepPatternParts[i];
				if (part.startsWith("{") && part.endsWith("}")) {
					patternVarsNames.add(part.substring(1, part.length()-1));
					part = "\\" + part.substring(0, part.length()-1) + "\\}";
					prepPattern = prepPattern.replaceAll(part, "([^//]+)");
				}
			}
			
			String urlPattern = "^" + prepPattern + "$";
			 
			Pattern pattern = Pattern.compile(urlPattern);
			Matcher matcher = pattern.matcher(reqUrl);
			
			if (matcher.matches()) {
				String[] reqUrlParts = reqUrl.split("/");
				for (int i = 0; i < patternVarsNames.size(); i++) {
					String patternVarName = patternVarsNames.get(i);
					String value = reqUrlParts[i];
					request.setAttribute(patternVarName, value);
				}
				String forwardServlet = entry.getValue();
				RequestDispatcher dispatcher = request.getRequestDispatcher(forwardServlet);
				dispatcher.forward(request, response);
				return;
			}
		}
	}
}
