package com.vit.Schedule.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

@WebListener
public class Log4jConfigListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String log4jConfigFile = "WEB-INF/log4j.properties";
		String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		System.out.println(fullPath);
		PropertyConfigurator.configure(fullPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
