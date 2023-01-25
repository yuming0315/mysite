package com.douzone.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextLoadListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce)  { 
        ServletContext sc = sce.getServletContext();
        String contextConfigLocation = sc.getInitParameter("contextConfigLocation");
		
        System.out.println(contextConfigLocation);
		System.out.println("Application Starts....");
    }
	
    public void contextDestroyed(ServletContextEvent sce)  { 
    	System.out.println("Application end");
    }

   
}
