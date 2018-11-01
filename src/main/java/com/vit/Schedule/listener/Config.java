package com.vit.Schedule.listener;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;

import com.vit.Schedule.servlet.MySQLFiller;

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
public class Config implements ServletContextListener {
	
	private static Logger log = Logger.getLogger(Config.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		MySQLFiller filler = new MySQLFiller();
		try {
			log.info("Started creating schema...");
			filler.init();
			log.info("Schema was successfully created!");
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
