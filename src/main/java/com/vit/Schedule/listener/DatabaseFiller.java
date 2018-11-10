package com.vit.Schedule.listener;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;

import com.vit.Schedule.init.FillerServlet;
import com.vit.Schedule.util.Settings;

/**
 * You can do the same with xml version:
 * <listener>
 *    <listener-class>com.vit.Schedule.listener.Config</listener-class>
 * </listener>
 * 
 * <servlet>  
 *    <servlet-name>servlet1</servlet-name>  
 *    <servlet-class>com.vit.Schedule.servlet.MySQLFiller</servlet-class>  
 *    <load-on-startup>1</load-on-startup>  
 * </servlet>  
 * 
 * @author User
 *
 */

@WebListener
public class DatabaseFiller implements ServletContextListener {
	private static Settings settings = Settings.getInstance();
	private static FillerServlet filler = FillerServlet.getInstance(); 

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		filler.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		if (filler.isCreateAndDrop() && !settings.value("jdbc.url").contains("h2")) {
			try {
				FillerServlet.getInstance().dropDatabase();
			} catch (Exception e) {
			}
		}
	}
}
