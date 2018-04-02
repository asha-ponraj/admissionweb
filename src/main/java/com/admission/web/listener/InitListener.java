package com.admission.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.admission.util.Profile;

public class InitListener implements ServletContextListener {
	private static final Logger log = Logger.getLogger(InitListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Profile.CONTEXT_PATH = sce.getServletContext().getContextPath().substring(1);
		log.info("Webapp Name: " + Profile.CONTEXT_PATH);
		
	}

}
